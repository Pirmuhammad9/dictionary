package uz.gita.dictionaryxp.data.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class DBHelper2 extends SQLiteOpenHelper {
    private Context context;
    private SQLiteDatabase mDatabase;
    private String mDatabaseName;

    protected DBHelper2( Context context,String name, int version) {
        super(context, name, null, version);
        this.context = context;
        this.mDatabaseName = name;
        if (!isExist()) {
            getReadableDatabase();
            if (!isOpenDatabase()) {
               return;
            }
            openDatabase();
        }
    }
    public SQLiteDatabase database() {
        return mDatabase;
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        db.disableWriteAheadLogging();
    }
    @Override
    public synchronized void close() {
        if (mDatabase != null)
            mDatabase.close();
        SQLiteDatabase.releaseMemory();
        super.close();
    }


    private boolean isExist() {
        File databese = context.getApplicationContext().getDatabasePath(mDatabaseName);
        return databese.exists();
    }

    private boolean isOpenDatabase() {
        try {
            InputStream inputStream = context.getAssets().open(mDatabaseName);
            String outFileName = context.getDatabasePath(mDatabaseName).getAbsolutePath();
            OutputStream outputStream = new FileOutputStream(outFileName);
            byte[] buff = new byte[1024];
            int length;
            while ((length = inputStream.read(buff)) > 0) {
                outputStream.write(buff, 0, length);
            }
            outputStream.flush();
            outputStream.close();
            return true;


        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void openDatabase() {
        if (mDatabase != null && mDatabase.isOpen())
            return;
        String dbPath = context.getDatabasePath(mDatabaseName).getPath();
        mDatabase = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READWRITE);

    }

    public void closeDatabase() {
        if (mDatabase != null)
            mDatabase.close();
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        closeDatabase();
    }

}
