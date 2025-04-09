activity_main.xml

<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent" android:layout_height="match_parent"
    android:orientation="vertical" android:padding="16dp">
    <EditText android:id="@+id/n" android:layout_width="match_parent"
        android:layout_height="wrap_content" android:hint="Name" />
    <EditText android:id="@+id/m" android:layout_width="match_parent"
        android:layout_height="wrap_content" android:hint="Marks" android:inputType="number" />
    <Button android:id="@+id/s" android:layout_width="match_parent"
        android:layout_height="wrap_content" android:text="Submit" />
    <Button android:id="@+id/v" android:layout_width="match_parent"
        android:layout_height="wrap_content" android:text="View" />
    <TableLayout android:id="@+id/t" android:layout_width="match_parent"
        android:layout_height="wrap_content" />
</LinearLayout>

MainActivity.java
package com.example.db;

import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import android.database.*;

public class MainActivity extends Activity {
    EditText n, m;
    TableLayout t;
    DBHelper d;

    @Override
    protected void onCreate(Bundle s) {
        super.onCreate(s);
        setContentView(R.layout.activity_main);
        d = new DBHelper(this);
        n = findViewById(R.id.n);
        m = findViewById(R.id.m);
        t = findViewById(R.id.t);

        findViewById(R.id.s).setOnClickListener(v -> {
            d.add(n.getText().toString(), Integer.parseInt(m.getText().toString()));
            Toast.makeText(this, "Added", Toast.LENGTH_SHORT).show();
            n.setText(""); m.setText("");
        });

        findViewById(R.id.v).setOnClickListener(v -> {
            t.removeAllViews();

            // Add header row
            TableRow h = new TableRow(this);
            TextView h1 = new TextView(this); h1.setText("Name");
            TextView h2 = new TextView(this); h2.setText("Marks");
            h.addView(h1); h.addView(h2); t.addView(h);

            Cursor c = d.get();
            while(c.moveToNext()) {
                TableRow r = new TableRow(this);
                TextView v1 = new TextView(this); v1.setText(c.getString(1));
                TextView v2 = new TextView(this); v2.setText(c.getString(2));
                r.addView(v1); r.addView(v2); t.addView(r);
            }
        });
    }
}

DBHelper.java
package com.example.db;
import android.content.*;
import android.database.*;
import android.database.sqlite.*;
public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context c) { super(c, "sdb", null, 1); }
    @Override
    public void onCreate(SQLiteDatabase d) {
        d.execSQL("CREATE TABLE s (id INTEGER PRIMARY KEY AUTOINCREMENT, n TEXT, m INTEGER)");
    }
    @Override
    public void onUpgrade(SQLiteDatabase d, int o, int n) {}
    public void add(String n, int m) {
        ContentValues c = new ContentValues();
        c.put("n", n); c.put("m", m);
        getWritableDatabase().insert("s", null, c);
    }
    public Cursor get() { return getReadableDatabase().rawQuery("SELECT * FROM s", null); }
}
AndriodMainfest.xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools">

    <application
        android:allowBackup="true"
        android:dataExtractionRules="@xml/data_extraction_rules"
        android:fullBackupContent="@xml/backup_rules"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/Theme.Db"
        tools:targetApi="31">
        <activity
            android:name=".MainActivity"
            android:exported="true">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
    </application>

</manifest>
