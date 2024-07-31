package mx.ipn.escom.guarderiaapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import mx.ipn.escom.guarderiaapp.databinding.ActivityRegisterBinding

class Register : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegister.setOnClickListener {
            if(binding.emailTextView.text.isEmpty() or
                binding.passwordTextView.text.isEmpty() or
                binding.usertextView.text.isEmpty()){
                Toast.makeText(this, "Parametros incompletos, intente de nuevo", Toast.LENGTH_SHORT).show()
            }
            else if(binding.emailTextView.text.isNotEmpty() &&
                binding.passwordTextView.text.isNotEmpty() &&
                binding.usertextView.text.isNotEmpty()){
                val user = binding.usertextView.text.toString()
                val email = binding.emailTextView.text.toString()
                val password = binding.passwordTextView.text.toString()

                FirebaseAuth.getInstance()
                    .createUserWithEmailAndPassword(email,
                        password).addOnCompleteListener {
                        if(it.isSuccessful){
                            savedata(user,email, password)
                        }else if (binding.passwordTextView.text.toString().length < 6){
                            showAlertpassword()
                        }else{
                            showAlert()
                        }
                    }
            }
        }
    }
    private fun savedata(user: String,email: String, password: String) {
        val uid = auth.currentUser?.uid.toString()

        val map: HashMap<String, Any> = HashMap()
        map["uid"] = uid
        map["user"] = user
        map["email"] = email ?: ""
        map["password"] = password ?: ""

        db.collection("tutores").document(uid).set(map).addOnCompleteListener {
            if(it.isSuccessful){
                showHome(binding.emailTextView.text.toString())
            }else{
                showAlert()
            }
        }
    }
    private fun showAlert(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error")
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
}