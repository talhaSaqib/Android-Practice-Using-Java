package com.example.permissions;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{

    private static final int CONTACTS_PERMISSION_CODE = 1;
    private static final int STORAGE_PERMISSION_CODE = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void readContacts(View view)
    {
        if (checkPermission(Manifest.permission.READ_CONTACTS, CONTACTS_PERMISSION_CODE))
        {
            Toast.makeText(this, "Read Contacts Button Tapped", Toast.LENGTH_SHORT).show();
        }
    }

    public void readExternalStorage(View view)
    {
        if (checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE, STORAGE_PERMISSION_CODE))
        {
            Toast.makeText(this, "Read External Storage Button Tapped", Toast.LENGTH_SHORT).show();
        }
    }


    public boolean checkPermission(String permission, int requestCode)
    {
        // Checking if permission is not granted
        if (ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_DENIED)
        {
            ActivityCompat.requestPermissions(this, new String[] { permission }, requestCode);
            return false;
        }
        else
        {
            Toast.makeText(MainActivity.this, "Permission already granted", Toast.LENGTH_SHORT).show();
            return true;
        }
    }

// This function is called when user accept or decline the permission.
// Request Code is used to check which permission called this function.
// This request code is provided when user is prompt for permission.

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == CONTACTS_PERMISSION_CODE)
        {
            // Checking whether user granted the permission or not.
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this, "Read Contacts Button Tapped", Toast.LENGTH_SHORT).show();
            }
            else
            {
                findViewById(R.id.button).setEnabled(false);
                Toast.makeText(MainActivity.this,"Contacts Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
        else if (requestCode == STORAGE_PERMISSION_CODE)
        {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this, "Read Contacts Button Tapped", Toast.LENGTH_SHORT).show();
            }
            else
            {
                findViewById(R.id.button2).setEnabled(false);
                Toast.makeText(MainActivity.this,"Storage Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
        // 'else ifs' of other permissions

    }

}
