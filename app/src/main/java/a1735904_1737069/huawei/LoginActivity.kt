package a1735904_1737069.huawei

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.huawei.agconnect.auth.AGConnectAuth
import com.huawei.agconnect.auth.HwIdAuthProvider
import com.huawei.hms.support.hwid.HuaweiIdAuthManager
import com.huawei.hms.support.hwid.request.HuaweiIdAuthParams
import com.huawei.hms.support.hwid.request.HuaweiIdAuthParamsHelper
import com.huawei.hms.support.hwid.ui.HuaweiIdAuthButton


class LoginActivity : AppCompatActivity() {
    private val HUAWEIID_SIGNIN = 1001
    private var userName: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val currentUser = AGConnectAuth.getInstance().currentUser
        if (currentUser != null) {
            val intent = Intent(this, SearchActivity::class.java)
            intent.putExtra("USER_NAME", currentUser.displayName)
            startActivity(intent)
            finish()
        } else {
            val loginHuaweiButton = findViewById<HuaweiIdAuthButton>(R.id.huaweiIdAuthButton)
            loginHuaweiButton.setOnClickListener {
                val authParams = HuaweiIdAuthParamsHelper(HuaweiIdAuthParams.DEFAULT_AUTH_REQUEST_PARAM).setAccessToken().createParams()
                val service = HuaweiIdAuthManager.getService(this, authParams)
                startActivityForResult(service.signInIntent, HUAWEIID_SIGNIN)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == HUAWEIID_SIGNIN) {
            val authHuaweiIdTask = HuaweiIdAuthManager.parseAuthResultFromIntent(data)
            if (authHuaweiIdTask.isSuccessful) {
                val huaweiAccount = authHuaweiIdTask.result
                val accessToken = huaweiAccount.accessToken
                val credential = HwIdAuthProvider.credentialWithToken(accessToken)
                AGConnectAuth.getInstance().signIn(credential)
                    .addOnSuccessListener { signInResult ->
                        val user = signInResult.user

                        Toast.makeText(this, "Welcome " + user.displayName, Toast.LENGTH_LONG).show()
                        val intent = Intent(this, SearchActivity::class.java)
                        intent.putExtra("USER_NAME", user.displayName)
                        startActivity(intent)
                        finish()
                    }.addOnFailureListener { exception ->
                        Toast.makeText(this, "Authentication failed: " + exception.message, Toast.LENGTH_LONG).show()
                    }
            } else {
                Toast.makeText(this, "HwID signIn failed: " + authHuaweiIdTask.exception.message, Toast.LENGTH_LONG).show()
            }
        }
    }

}
