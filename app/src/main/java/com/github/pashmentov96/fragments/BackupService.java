package com.github.pashmentov96.fragments;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.List;

public class BackupService extends Service {
    private static final String LOG = "MyLogs";
    private static final String ADD_BACKUP = "AddBackup";
    private static final String READ_BACKUP = "ReadBackup";
    private static final String GET_ALL_BACKUPS = "GetAllBackups";
    private static final String FILENAME = "Filename";
    private static Context context;

    public static void addBackup(Context context) {
        Log.d(LOG, "addBackup");
        BackupService.context = context;
        Intent intent = new Intent(context, BackupService.class);
        intent.setAction(ADD_BACKUP);
        context.startService(intent);
    }

    public static void readBackup(Context context, String filename) {
        Log.d(LOG, "readBackup");
        BackupService.context = context;
        Intent intent = new Intent(context, BackupService.class);
        intent.setAction(READ_BACKUP);
        intent.putExtra(FILENAME, filename);
        context.startService(intent);
    }

    public static void getAllBackups(Context context) {
        Log.d(LOG, "getAllBackups");
        BackupService.context = context;
        Intent intent = new Intent(context, BackupService.class);
        intent.setAction(GET_ALL_BACKUPS);
        context.startService(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(LOG, "onStartCommand");

        switch (intent.getAction()) {
            case ADD_BACKUP:
                String filename = "backup-" + String.valueOf(Calendar.getInstance().getTime().getTime());
                Log.d(LOG, filename);

                FileOutputStream outputStream;
                try {
                    outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
                    PersonRepository personRepository = new PersonRepository(context);
                    List<Person> personList = personRepository.loadPersons();
                    outputStream.write(personList.toString().getBytes());
                    Log.d(LOG, personList.toString());
                    outputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case READ_BACKUP:
                String backupFilename = intent.getStringExtra("FILENAME");
                FileInputStream input;
                try {
                    input = openFileInput(backupFilename);
                    byte[] bytes = new byte[input.available()];
                    int result = input.read(bytes);
                    Log.d(LOG, "!!! + " + result + " = " + new String(bytes));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case GET_ALL_BACKUPS:
                String[] files = context.fileList();
                StringBuilder filesToString = new StringBuilder();
                for (int i = 0; i < files.length; ++i) {
                    filesToString.append(files[i]);
                    filesToString.append("\n");
                }
                Log.d(LOG, "all files\n" + filesToString.toString());
                break;
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}