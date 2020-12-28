package com.test.m281220;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.neovisionaries.ws.client.ThreadType;
import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketFactory;
import com.neovisionaries.ws.client.WebSocketFrame;
import com.neovisionaries.ws.client.WebSocketListener;
import com.neovisionaries.ws.client.WebSocketState;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.net.ssl.SSLContext;



public class SocketManger {


    public SPreferences sPreferences;
    String API_BASE_URL = "wss://server.river-talks.com:8765";
    public static WebSocket ws;
    private static final String TAG = "My Log Test";


    public interface SocketManagerCallback {
        void result(SocketManagerResponseDTO socketManagerResponseDTO);
    }


    public void setPreferences (SPreferences sPreferences){
        this.sPreferences = sPreferences;
    }

    private final Map<String, SocketManagerCallback> map = new ConcurrentHashMap<>();


    public void addListener(String tag, SocketManagerCallback socketManagerCallback) {
        Log.e("SocketManger", "addListener tag = " + tag);

        map.put(tag, socketManagerCallback);
    }


    public void removeListener(String tag) {
        map.remove(tag);
    }


    private AtomicBoolean isConnected = new AtomicBoolean(false);
    private AtomicBoolean isOnError = new AtomicBoolean(false);

//ЗАПРОС ДЛЯ ПОЛУЧЕНИЯ ЧАТОВ ОНлайн
    public class MethodChatGetOnline {
        String method = "chat/get";
    }

    public SocketManger() {
        Thread thread1 = new Thread() {


            @Override
            public void run() {

                WebSocketFactory factory = new WebSocketFactory().setConnectionTimeout(5000);

                SSLContext context = null;
                try {
                    context = NaiveSSLContext.getInstance("TLS");
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
                factory.setSSLContext(context);
                factory.setVerifyHostname(false);

                try {
                    ws = factory.createSocket(API_BASE_URL);


                    ws.addListener(new WebSocketAdapter() {


                        @Override
                        public void onFrameError(WebSocket websocket, WebSocketException cause, WebSocketFrame frame) throws Exception {
                            super.onFrameError(websocket, cause, frame);
                            Log.w("TAG", "onError3");
                        }

                        @Override
                        public void onMessageError(WebSocket websocket, WebSocketException cause, List<WebSocketFrame> frames) throws Exception {
                            super.onMessageError(websocket, cause, frames);
                            Log.w("TAG", "onError2");
                        }

                        @Override
                        public void onUnexpectedError(WebSocket websocket, WebSocketException cause) throws Exception {
                            super.onUnexpectedError(websocket, cause);
                            Log.w("TAG", "onError1");
                        }

                        @Override
                        public void onTextMessage(WebSocket websocket, String message) {
                            System.out.println(message);

                           // Log.d(TAG, "onTextMessage: " + message +  " thread = " +Thread.currentThread().getId());

                            JsonObject jsonObject = new JsonParser().parse(message).getAsJsonObject();
                            String method = jsonObject.get("method").getAsString();

                            System.out.println(method);


                            if (method.equals("user/login")) {
                                SocketManagerResponseDTO socketManagerResponseDTO = new SocketManagerResponseDTO(SocketManagerResponseDTO.USER_LOGIN, message);
                                Collection<SocketManagerCallback> socketManagerCallbackMap = map.values();
                                for (SocketManagerCallback s : socketManagerCallbackMap) {
                                    s.result(socketManagerResponseDTO);

                                }

                            }  else if (method.equals("user/sms")) {
                                SocketManagerResponseDTO socketManagerResponseDTO = new SocketManagerResponseDTO(SocketManagerResponseDTO.USER_SMS, message);
                                Collection<SocketManagerCallback> socketManagerCallbackMap = map.values();
                                for (SocketManagerCallback s : socketManagerCallbackMap) {
                                    s.result(socketManagerResponseDTO);
                                }


                            } else if (method.equals("message/get") | (method.equals("message/sent"))) {
                                Log.e("SocketManger", "message = " + message);
                                SocketManagerResponseDTO socketManagerResponseDTO = new SocketManagerResponseDTO(SocketManagerResponseDTO.MESSAGE_GET, message);
                                Collection<SocketManagerCallback> socketManagerCallbackMap = map.values();
                                for (SocketManagerCallback s : socketManagerCallbackMap) {
                                    s.result(socketManagerResponseDTO);

                                }

                                MethodChatGetOnline methodChatGetOnline = new MethodChatGetOnline();
                                try {
                                    Gson gson = new Gson();
                                    String json20 = gson.toJson(methodChatGetOnline);
                                    System.out.println(json20);
                                    ws.sendText(json20);

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }





                            }  else if (method.equals("user/register")) {
                            SocketManagerResponseDTO socketManagerResponseDTO = new SocketManagerResponseDTO(SocketManagerResponseDTO.USER_REGISTER, message);
                            Collection<SocketManagerCallback> socketManagerCallbackMap = map.values();
                            for (SocketManagerCallback s : socketManagerCallbackMap) {
                                s.result(socketManagerResponseDTO);
                            }

                            } else if (method.equals("chat/get")) {
                                    SocketManagerResponseDTO socketManagerResponseDTO = new SocketManagerResponseDTO(SocketManagerResponseDTO.CHAT_GET, message);
                                    Collection<SocketManagerCallback> socketManagerCallbackMap = map.values();
                                    for (SocketManagerCallback s : socketManagerCallbackMap) {
                                        s.result(socketManagerResponseDTO);
                                }

                            } else if (method.equals("user/search")) {
                                SocketManagerResponseDTO socketManagerResponseDTO = new SocketManagerResponseDTO(SocketManagerResponseDTO.USER_SEARCH, message);
                                Collection<SocketManagerCallback> socketManagerCallbackMap = map.values();
                                for (SocketManagerCallback s : socketManagerCallbackMap) {
                                    s.result(socketManagerResponseDTO);
                                }

                            } else if (method.equals("contact/get") | (method.equals("contact/get-requests"))) {
                                SocketManagerResponseDTO socketManagerResponseDTO = new SocketManagerResponseDTO(SocketManagerResponseDTO.CONTACT_GET, message);
                                Collection<SocketManagerCallback> socketManagerCallbackMap = map.values();
                                for (SocketManagerCallback s : socketManagerCallbackMap) {
                                    s.result(socketManagerResponseDTO);
                                }

                            } else if (method.equals("avatar/get")) {
                                SocketManagerResponseDTO socketManagerResponseDTO = new SocketManagerResponseDTO(SocketManagerResponseDTO.AVATAR_GET, message);
                                Collection<SocketManagerCallback> socketManagerCallbackMap = map.values();
                                for (SocketManagerCallback s : socketManagerCallbackMap) {
                                    s.result(socketManagerResponseDTO);
                                }

                            } else if (method.equals("contact/search")) {
                                SocketManagerResponseDTO socketManagerResponseDTO = new SocketManagerResponseDTO(SocketManagerResponseDTO.CONTACT_SEARCH, message);
                                Collection<SocketManagerCallback> socketManagerCallbackMap = map.values();
                                for (SocketManagerCallback s : socketManagerCallbackMap) {
                                    s.result(socketManagerResponseDTO);
                                }

                            } else if (method.equals("avatar/set")) {
                                SocketManagerResponseDTO socketManagerResponseDTO = new SocketManagerResponseDTO(SocketManagerResponseDTO.AVATAR_SET, message);
                                Collection<SocketManagerCallback> socketManagerCallbackMap = map.values();
                                for (SocketManagerCallback s : socketManagerCallbackMap) {
                                    s.result(socketManagerResponseDTO);
                                }

                            } else if (method.equals("contact/delete")) {
                                SocketManagerResponseDTO socketManagerResponseDTO = new SocketManagerResponseDTO(SocketManagerResponseDTO.CONTACT_DELETE, message);
                                Collection<SocketManagerCallback> socketManagerCallbackMap = map.values();
                                for (SocketManagerCallback s : socketManagerCallbackMap) {
                                    s.result(socketManagerResponseDTO);
                                }

                            } else if (method.equals("contact/add")) {
                                SocketManagerResponseDTO socketManagerResponseDTO = new SocketManagerResponseDTO(SocketManagerResponseDTO.CONTACT_ADD, message);
                                Collection<SocketManagerCallback> socketManagerCallbackMap = map.values();
                                for (SocketManagerCallback s : socketManagerCallbackMap) {
                                    s.result(socketManagerResponseDTO);
                                }

                            } else if (method.equals("group/get")) {
                                SocketManagerResponseDTO socketManagerResponseDTO = new SocketManagerResponseDTO(SocketManagerResponseDTO.GROUP_GET, message);
                                Collection<SocketManagerCallback> socketManagerCallbackMap = map.values();
                                for (SocketManagerCallback s : socketManagerCallbackMap) {
                                    s.result(socketManagerResponseDTO);
                                }

                            } else if (method.equals("group/new")) {
                                SocketManagerResponseDTO socketManagerResponseDTO = new SocketManagerResponseDTO(SocketManagerResponseDTO.GROUP_NEW, message);
                                Collection<SocketManagerCallback> socketManagerCallbackMap = map.values();
                                for (SocketManagerCallback s : socketManagerCallbackMap) {
                                    s.result(socketManagerResponseDTO);
                                }

                            } else if (method.equals("group/search")) {
                                SocketManagerResponseDTO socketManagerResponseDTO = new SocketManagerResponseDTO(SocketManagerResponseDTO.GROUP_SEARCH, message);
                                Collection<SocketManagerCallback> socketManagerCallbackMap = map.values();
                                for (SocketManagerCallback s : socketManagerCallbackMap) {
                                    s.result(socketManagerResponseDTO);
                                }

                            } else if (method.equals("message/get_group")) {
                                SocketManagerResponseDTO socketManagerResponseDTO = new SocketManagerResponseDTO(SocketManagerResponseDTO.MESSAGE_GET_GROUP, message);
                                Collection<SocketManagerCallback> socketManagerCallbackMap = map.values();
                                for (SocketManagerCallback s : socketManagerCallbackMap) {
                                    s.result(socketManagerResponseDTO);
                                }

                            }else if (method.equals("file/get")) {
                                SocketManagerResponseDTO socketManagerResponseDTO = new SocketManagerResponseDTO(SocketManagerResponseDTO.FILE_GET, message);
                                Collection<SocketManagerCallback> socketManagerCallbackMap = map.values();
                                for (SocketManagerCallback s : socketManagerCallbackMap) {
                                    s.result(socketManagerResponseDTO);
                                }
                            }

                        }

                        @Override
                        public void onError(WebSocket websocket, WebSocketException cause) throws Exception {
                            super.onError(websocket, cause);
                            Log.w("TAG", "onError4", cause);

                            isOnError.set(true);
                            while (isOnError.get()) {

                                try {
                                    Log.w("TAG22", "try connect" + " Thread = "+ Thread.currentThread().getId());
                                    ws = ws.recreate().connect();
                                    isOnError.set(false);
                                    String method1 = "user/login";
                                    String token = sPreferences.getToken();
                                    TokenDTO tokenDTO = new TokenDTO(method1, token);
                                    Gson gson = new Gson();
                                    String json9 = gson.toJson(tokenDTO);
                                    ws.sendText(String.valueOf(json9));

                                } catch (Exception e) {
                                    Log.w("TAG22", "catch " + " Thread = "+ Thread.currentThread().getId());
                                    isOnError.set(false);
                                    Thread.sleep(5_000);
                                }

                            }

                        }



                    }).addListener(new WebSocketListener() {
                        @Override
                        public void onStateChanged(WebSocket websocket, WebSocketState newState) throws Exception {
                            Log.w("TAG", "onStatecvhanged");


                        }

                        @Override
                        public void onConnected(WebSocket websocket, Map<String, List<String>> headers) throws Exception {
                            Log.w("TAG", "onConnected");
                            isConnected.set(true);


                        }

                        @Override
                        public void onConnectError(WebSocket websocket, WebSocketException cause) throws Exception {
                            Log.w("TAG", "onConnectError");


                            isConnected.set(false);

                            while (!isConnected.get()) {

                                try {
                                    Log.w("TAG22", "try connect" + " Thread = " + Thread.currentThread().getId());
                                    ws = ws.recreate().connect();
                                    isConnected.set(true);
                                    String method1 = "user/login";
                                    String token = sPreferences.getToken();
                                    TokenDTO tokenDTO = new TokenDTO(method1, token);
                                    Gson gson = new Gson();
                                    String json9 = gson.toJson(tokenDTO);
                                    ws.sendText(String.valueOf(json9));

                                } catch (Exception e) {
                                    Log.w("TAG22", "catch " + " Thread = " + Thread.currentThread().getId());
                                    isConnected.set(false);
                                    Thread.sleep(5_000);
                                }
                            }
                        }

                        @Override
                        public void onDisconnected(WebSocket websocket, WebSocketFrame serverCloseFrame, WebSocketFrame clientCloseFrame, boolean closedByServer) throws Exception {
                            isConnected.set(false);
                            Log.w("TAG22", "onStatecvhanged4"+ " isConnected = " +isConnected.get()  + " Thread = "+ Thread.currentThread().getId());

                            while (!isConnected.get()) {

                                try {
                                    Log.w("TAG22", "try connect" + " Thread = "+ Thread.currentThread().getId());
                                    ws = ws.recreate().connect();
                                    isConnected.set(true);
                                    String method1 = "user/login";
                                    String token = sPreferences.getToken();
                                    TokenDTO tokenDTO = new TokenDTO(method1, token);
                                    Gson gson = new Gson();
                                    String json9 = gson.toJson(tokenDTO);
                                    ws.sendText(String.valueOf(json9));

                                } catch (Exception e) {
                                    Log.w("TAG22", "catch " + " Thread = "+ Thread.currentThread().getId());
                                    isConnected.set(false);
                                    Thread.sleep(5_000);
                                }

                            }
                          //  Log.w("TAG22", "exit while" + " Thread = "+Thread.currentThread().getId());
                        }

                        @Override
                        public void onFrame(WebSocket websocket, WebSocketFrame frame) throws Exception {
                           // Log.w("TAG", "onFrame");
                        }

                        @Override
                        public void onContinuationFrame(WebSocket websocket, WebSocketFrame frame) throws Exception {
                            Log.w("TAG", "onContinuationFrame");
                        }

                        @Override
                        public void onTextFrame(WebSocket websocket, WebSocketFrame frame) throws Exception {
                          //  Log.w("TAG", "onTextFrame");


                        }

                        @Override
                        public void onBinaryFrame(WebSocket websocket, WebSocketFrame frame) throws Exception {
                          //  Log.w("TAG", "onStatecvhanged8");
                        }

                        @Override
                        public void onCloseFrame(WebSocket websocket, WebSocketFrame frame) throws Exception {

                        }

                        @Override
                        public void onPingFrame(WebSocket websocket, WebSocketFrame frame) throws Exception {
                           // Log.w("TAG", "onPingFrame");
                        }

                        @Override
                        public void onPongFrame(WebSocket websocket, WebSocketFrame frame) throws Exception {
                           // Log.w("TAG", "onPongFrame");
                        }

                        @Override
                        public void onTextMessage(WebSocket websocket, String message) throws Exception {
                           // Log.w("TAG", "onTextMessage" + message);
                        }

                        @Override
                        public void onTextMessage(WebSocket websocket, byte[] data) throws Exception {
                          //  Log.w("TAG", "onTextMessageData");
                        }

                        @Override
                        public void onBinaryMessage(WebSocket websocket, byte[] binary) throws Exception {

                        }

                        @Override
                        public void onSendingFrame(WebSocket websocket, WebSocketFrame frame) throws Exception {

                        }

                        @Override
                        public void onFrameSent(WebSocket websocket, WebSocketFrame frame) throws Exception {

                        }

                        @Override
                        public void onFrameUnsent(WebSocket websocket, WebSocketFrame frame) throws Exception {

                        }

                        @Override
                        public void onThreadCreated(WebSocket websocket, ThreadType threadType, Thread thread) throws Exception {
                            Log.w("TAG", "onThreadCreated");

                        }

                        @Override
                        public void onThreadStarted(WebSocket websocket, ThreadType threadType, Thread thread) throws Exception {
                            Log.w("TAG", "onThreadStarted");

                        }

                        @Override
                        public void onThreadStopping(WebSocket websocket, ThreadType threadType, Thread thread) throws Exception {

                        }

                        @Override
                        public void onError(WebSocket websocket, WebSocketException cause) throws Exception {
                            Log.w("TAG", "onError");

                            isOnError.set(true);
                            while (isOnError.get()) {

                                try {
                                    Log.w("TAG22", "try connect" + " Thread = "+ Thread.currentThread().getId());
                                    ws = ws.recreate().connect();
                                    isOnError.set(false);
                                    String method1 = "user/login";
                                    String token = sPreferences.getToken();
                                    TokenDTO tokenDTO = new TokenDTO(method1, token);
                                    Gson gson = new Gson();
                                    String json9 = gson.toJson(tokenDTO);
                                    ws.sendText(String.valueOf(json9));

                                } catch (Exception e) {
                                    Log.w("TAG22", "catch " + " Thread = "+ Thread.currentThread().getId());
                                    isOnError.set(false);
                                    Thread.sleep(5_000);
                                }

                            }
                        }

                        @Override
                        public void onFrameError(WebSocket websocket, WebSocketException cause, WebSocketFrame frame) throws Exception {

                        }

                        @Override
                        public void onMessageError(WebSocket websocket, WebSocketException cause, List<WebSocketFrame> frames) throws Exception {
                            Log.w("TAG", "onMessageError");

                        }

                        @Override
                        public void onMessageDecompressionError(WebSocket websocket, WebSocketException cause, byte[] compressed) throws Exception {

                        }

                        @Override
                        public void onTextMessageError(WebSocket websocket, WebSocketException cause, byte[] data) throws Exception {
                            Log.w("TAG", "onTextMessageError");

                        }

                        @Override
                        public void onSendError(WebSocket websocket, WebSocketException cause, WebSocketFrame frame) throws Exception {
                            Log.w("TAG", "onSendError");

                        }

                        @Override
                        public void onUnexpectedError(WebSocket websocket, WebSocketException cause) throws Exception {

                        }

                        @Override
                        public void handleCallbackError(WebSocket websocket, Throwable cause) throws Exception {
                           // Log.w("TAG", "handleCallbackError");

                        }

                        @Override
                        public void onSendingHandshake(WebSocket websocket, String requestLine, List<String[]> headers) throws Exception {
                            Log.w("TAG", "onStatecvhanged20");

                        }
                    });


                    ws.connectAsynchronously();
                } catch (IOException e) {
                    e.printStackTrace();

                }



            }

        };
        thread1.start();
    }


}
