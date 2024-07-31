package mx.ipn.escom.guarderiaapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import mx.ipn.escom.guarderiaapp.databinding.ActivityLogInScreenBinding


class LogInScreen : AppCompatActivity() {
    private lateinit var binding: ActivityLogInScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogInScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = FirebaseAuth.getInstance().currentUser
        if (user != null){

            val EMAIL = user.email
            binding.EmailTextView.setText(EMAIL)
        }
        //Setup
        setup()
    }
    private fun setup(){
        title = "Autenticación"

        binding.btnRegister.setOnClickListener {
            registerscreen()
        }
        binding.btnLogin.setOnClickListener {
            if(binding.EmailTextView.text.isEmpty() or
                binding.passwordTextView.text.isEmpty()){
                Toast.makeText(this, "Parametros incompletos, intente de nuevo", Toast.LENGTH_SHORT).show()
            }
            else if(binding.EmailTextView.text.isNotEmpty() && binding.passwordTextView.text.isNotEmpty()){
                FirebaseAuth.getInstance()
                    .signInWithEmailAndPassword(binding.EmailTextView.text.toString(),
                        binding.passwordTextView.text.toString()).addOnCompleteListener {
                        if(it.isSuccessful){
                            showHome(it.result?.user?.email?:"")
                        }
                        else if (binding.passwordTextView.text.toString().length < 6){
                            showAlertpassword()
                        }else{
                            showAlert()
                        }
                    }

            }
        }

    }
    private fun showAlert(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error autenticando al usuario")
        builder.setPositiveButton("Aceptar",null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
    private fun showAlertpassword(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("La contraseña debe ser minimo de 6 caracteres")
        builder.setPositiveButton("Aceptar",null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
    private fun showHome(email: String){
        val homeIntent = Intent(this, MainActivity::class.java).apply {
            putExtra("email",email)
        }
        startActivity(homeIntent)
    }
    private fun registerscreen(){
        val registerIntent = Intent(this, Register::class.java).apply {}
        startActivity(registerIntent)
    }
}