package mx.ipn.escom.guarderiaapp

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener{
    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawerLayout = findViewById(R.id.drawer_layout)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)

        val toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav, R.string.close_nav)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, InicioFragment()).commit()
            navigationView.setCheckedItem(R.id.nav_inicio)
        }
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_inicio -> supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, InicioFragment()).commit()
            R.id.nav_actividades -> supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, ActividadesFragment()).commit()
            R.id.nav_incidencias -> supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, IncidenciasFragment()).commit()
            R.id.nav_observaciones -> supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, ObservacionesFragment()).commit()
            R.id.nav_cerrarsesion -> cerrarsesion()
                //Toast.makeText(this, "Logout!", Toast.LENGTH_SHORT).show()
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            onBackPressedDispatcher.onBackPressed()
        }
    }
    private fun cerrarsesion(){
        val positiveButtonClick = { dialog: DialogInterface, which: Int ->
            FirebaseAuth.getInstance().signOut()
            Toast.makeText(this, "Saliste", Toast.LENGTH_SHORT).show()
            val logoutIntent = Intent(this, LogInScreen::class.java).apply {}
            startActivity(logoutIntent)
        }
        val negativeButtonClick = { dialog: DialogInterface, which: Int ->
            Toast.makeText(this, android.R.string.no, Toast.LENGTH_SHORT).show()
        }

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Logout")
        builder.setMessage("¿Estas seguro que quieres salir?")
        builder.setPositiveButton("Aceptar", DialogInterface.OnClickListener(function = positiveButtonClick))
        builder.setNegativeButton(android.R.string.no, negativeButtonClick)
        builder.show()
    }

}