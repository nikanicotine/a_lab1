package com.example.a_lab1;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

import android.os.Handler;
import android.os.Message;

public class ThreadTask {
    Handler targetHandler;
    LogActivity.DatabaseHandlerUser dbUser;

    final Message message = Message.obtain();
//    LogActivity.DatabaseHandlerUser dbUser;

    ThreadTask(Handler main_handler, LogActivity.DatabaseHandlerUser dbUser1) {
        dbUser = dbUser1;
        this.targetHandler = main_handler;
    }

    public void sendLoginTask(String user, String password) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    // Имитируем высокую нагрузку
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                message.sendingUid = 1;
                if (dbUser.checkUser(user)) {
                    dbUser.sendUser(user, password);
                    message.obj = TRUE;
                } else message.obj = user;
                targetHandler.sendMessage(message);
            }
        }).start();
    }

    public void regUserTask(String login, String password) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    // Имитируем высокую нагрузку
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                message.sendingUid = 2;
                if (dbUser.checkUser(login)) {
                    message.obj = FALSE;
                } else {
                    LogActivity.User user = new LogActivity.User();
                    user.setLogin(login);
                    user.setPass(password);
                    dbUser.addUser(user);
                    message.obj = login;
                }
                targetHandler.sendMessage(message);

            }
        }).start();
    }

    public void removeUserTask(String login) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    // Имитируем высокую нагрузку
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                message.sendingUid = 3;
                if (!dbUser.checkUser(login)) {
                    message.obj = FALSE;
                } else {
                    dbUser.delUser(login);
                    message.obj = TRUE;
                }
                targetHandler.sendMessage(message);
            }
        }).start();
    }

    public void savePassTask(String login) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    // Имитируем высокую нагрузку
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                message.sendingUid = 4;
                if (!dbUser.checkUser(login)) {
                    message.obj = FALSE;
                } else {
                    dbUser.changePass(login);
                    message.obj = TRUE;
                }
                targetHandler.sendMessage(message);
            }
        }).start();
    }
}

