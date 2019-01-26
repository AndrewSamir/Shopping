package com.solution.internet.shopping.fragments;

import android.Manifest;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.developers.imagezipper.ImageZipper;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.solution.internet.shopping.R;
import com.solution.internet.shopping.adapters.AdapterChatMessage;
import com.solution.internet.shopping.interfaces.HandleRetrofitResp;
import com.solution.internet.shopping.models.ModelChatMessage.ModelChatMessage;
import com.solution.internet.shopping.models.ModelChatNewRequest.ModelChatNewRequest;
import com.solution.internet.shopping.models.ModelSignUpRequest.ModelSignUpRequest;
import com.solution.internet.shopping.retorfitconfig.HandleCalls;
import com.solution.internet.shopping.singleton.SingletonShopping;
import com.solution.internet.shopping.utlities.DataEnum;
import com.solution.internet.shopping.utlities.SharedPrefHelper;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import gun0912.tedbottompicker.TedBottomPicker;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;

public class ChatFragment extends BaseFragment implements HandleRetrofitResp {
    //region fields

    List<ModelChatMessage> modelChatMessageList;
    AdapterChatMessage adapterChatMessage;
    MultipartBody.Part profileImage;
    private Dialog dialogAddReset;

    //endregion

    //region views

    @BindView(R.id.rvChat)
    RecyclerView rvChat;

    @BindView(R.id.edtChatMessage)
    EditText edtChatMessage;
    //endregion

    //region life cycle

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        final View view = inflater.inflate(R.layout.chat_activity, container, false);

        unbinder = ButterKnife.bind(this, view);

        modelChatMessageList = new ArrayList<>();
        adapterChatMessage = new AdapterChatMessage(modelChatMessageList, getBaseActivity());
        rvChat.setLayoutManager(new LinearLayoutManager(getBaseActivity()));
        rvChat.setAdapter(adapterChatMessage);

        callChatWith();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        HandleCalls.getInstance(getBaseActivity()).setonRespnseSucess(this);
        SingletonShopping.getInstance().setOpenChatId(SingletonShopping.getInstance().getC_id());
        getBaseActivity().registerReceiver(mNotificationReceiver, new IntentFilter("KEY"));

    }

    @Override
    public void onPause() {
        super.onPause();
        SingletonShopping.getInstance().setOpenChatId(-1);
        getBaseActivity().unregisterReceiver(mNotificationReceiver);

    }

    @Override
    public void onStop() {
        super.onStop();
//        appHeader.setRight(0, 0, 0);
    }

    //endregion

    //region parent methods
    @Override
    protected boolean canShowAppHeader() {
        return false;
    }

    @Override
    protected boolean canShowBottomBar() {
        return false;
    }

    @Override
    protected boolean canShowBackArrow() {
        return false;
    }

    @Override
    protected String getTitle() {
        return null;
    }

    @Override
    public int getSelectedMenuId() {
        return 0;
    }

    //endregion

    //region calls response
    @Override
    public void onResponseSuccess(String flag, Object o) {
        Gson gson = new Gson();
        if (flag.equals(DataEnum.callChatWith.name())) {
            JsonArray jsonArray = gson.toJsonTree(o).getAsJsonArray();
            for (int i = 0; i < jsonArray.size(); i++) {
                ModelChatMessage modelChatMessage = gson.fromJson(jsonArray.get(i).getAsJsonObject(), ModelChatMessage.class);
                adapterChatMessage.addItem(modelChatMessage);

            }
            try {
                rvChat.smoothScrollToPosition(adapterChatMessage.getItemCount() - 1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (flag.equals(DataEnum.callUploadPhoto.name())) {
            String link = (String) o;
            callChatNew(link, "photo ", "price");
        }
    }


    @Override
    public void onNoContent(String flag, int code) {

    }

    @Override
    public void onResponseSuccess(String flag, Object o, int position) {

    }

    //endregion

    //region clicks
    @OnClick(R.id.btnChatSend)
    public void onClickbtnChatSend() {
        if (edtChatMessage.getText().toString().length() > 0)
            callChatNew(edtChatMessage.getText().toString(), "text", "price");
    }

    @OnClick(R.id.btnChatImg)
    public void onClickbtnChatImg() {
        final AlertDialog.Builder alert = new AlertDialog.Builder(getBaseActivity());

        alert.setMessage("choose");

        alert.setNegativeButton("صورة", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                if (ContextCompat
                        .checkSelfPermission(getBaseActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager
                        .PERMISSION_GRANTED && ContextCompat
                        .checkSelfPermission(getBaseActivity(), Manifest.permission.CAMERA) != PackageManager
                        .PERMISSION_GRANTED && ContextCompat
                        .checkSelfPermission(getBaseActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager
                        .PERMISSION_GRANTED) {

                    requestStorageAndCameraPermission();
                    return;
                } else {
                    selectImage();
                }
                dialog.dismiss();
            }
        });
        alert.setPositiveButton("فاتورة", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialogAddReset();
                dialog.dismiss();
            }
        });
        alert.show();
    }
    //endregion

    //region calls

    private void callChatWith() {
        Call call = HandleCalls.restShopping.getClientService().callChatWith(SingletonShopping.getInstance().getC_id());
        HandleCalls.getInstance(getBaseActivity()).callRetrofit(call, DataEnum.callChatWith.name(), true);
    }

    private void callChatNew(String message, String type, String price) {

        ModelChatNewRequest modelChatNewRequest = new ModelChatNewRequest();
        modelChatNewRequest.setMessage(message);
        modelChatNewRequest.setType(type);
        modelChatNewRequest.setPrice(price);
        modelChatNewRequest.setUserid(SingletonShopping.getInstance().getChatUserId());

        Date now = new Date();
        Long timeNow = now.getTime();

        if (type.equals("text")) {
            ModelChatMessage messagesChat = new ModelChatMessage();
            messagesChat.setReply(message);
            messagesChat.setTime(timeNow.intValue());
            messagesChat.setUserid(SharedPrefHelper.getInstance(getBaseActivity()).getUserid());
            messagesChat.setType(type);

            adapterChatMessage.addItem(messagesChat);
        } else if (type.equals("photo")) {
            ModelChatMessage messagesChat = new ModelChatMessage();
            messagesChat.setReply("http:\\/\\/pharaohsland.tours\\/tasawk\\/public\\/photos\\/" + message);
            messagesChat.setTime(timeNow.intValue());
            messagesChat.setUserid(SharedPrefHelper.getInstance(getBaseActivity()).getUserid());
            messagesChat.setType(type);

            adapterChatMessage.addItem(messagesChat);
        } else if (type.equals("invoice")) {
            ModelChatMessage messagesChat = new ModelChatMessage();
            messagesChat.setReply(message);
            messagesChat.setTime(timeNow.intValue());
            messagesChat.setUserid(SharedPrefHelper.getInstance(getBaseActivity()).getUserid());
            messagesChat.setType(type);

            adapterChatMessage.addItem(messagesChat);
        }
        try {
            rvChat.smoothScrollToPosition(adapterChatMessage.getItemCount() - 1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Call call = HandleCalls.restShopping.getClientService().callChatNew(modelChatNewRequest);
        HandleCalls.getInstance(getBaseActivity()).callRetrofit(call, DataEnum.callChatNew.name(), true);
    }

    private void callAcceptInvoice() {

        ModelChatNewRequest modelChatNewRequest = new ModelChatNewRequest();
        modelChatNewRequest.setCr_id(SingletonShopping.getInstance().getCr_id());
        Call call = HandleCalls.restShopping.getClientService().callAcceptInvoice(modelChatNewRequest);
        HandleCalls.getInstance(getBaseActivity()).callRetrofit(call, DataEnum.callAcceptInvoice.name(), true);
    }

    private void callUploadPhoto(MultipartBody.Part profileImage) {

        Call call = HandleCalls.restShopping.getClientService().callUploadPhoto(profileImage);
        HandleCalls.getInstance(getBaseActivity()).callRetrofit(call, DataEnum.callUploadPhoto.name(), true);
    }
    //endregion

    //region functions

    public static ChatFragment init() {
        return new ChatFragment();
    }

    private void dialogAddReset() {
        dialogAddReset = new Dialog(getBaseActivity());
        // Include dialog.xml file
        dialogAddReset.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogAddReset.setContentView(R.layout.dialog_add_reset);

        dialogAddReset.setCancelable(false);
        final TextView tvDialogExit = dialogAddReset.findViewById(R.id.tvDialogExit);
        final EditText edtDialogPrice = dialogAddReset.findViewById(R.id.edtDialogPrice);
        final EditText edtDialogMessage = dialogAddReset.findViewById(R.id.edtDialogMessage);


        dialogAddReset.show();

        Button btnDialogAction = dialogAddReset.findViewById(R.id.btnDialogAction);
        btnDialogAction.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (edtDialogPrice.getText().toString().length() == 0)
                    edtDialogPrice.setError(getString(R.string.required));
                else if (edtDialogMessage.getText().toString().length() == 0)
                    edtDialogMessage.setError(getString(R.string.required));
                else {
                    callChatNew(edtDialogMessage.getText().toString(), "invoice", edtDialogPrice.getText().toString());
                    dialogAddReset.dismiss();
                }

            }
        });

        tvDialogExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogAddReset.dismiss();
            }
        });

    }

    private BroadcastReceiver mNotificationReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            ModelChatMessage messagesChat;
            messagesChat = (ModelChatMessage) intent.getSerializableExtra("chatModel");
            adapterChatMessage.addItem(messagesChat);
            try {
                rvChat.smoothScrollToPosition(adapterChatMessage.getItemCount() - 1);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    };
    //endregion

    //region images
    private void requestStorageAndCameraPermission() {
        Dexter.withActivity(getBaseActivity())
                .withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
//                            chooseImage();
                            selectImage();
                        } else if (report.isAnyPermissionPermanentlyDenied()) {
//                            showMessage(R.string.please_grant_permissions);
                            Log.d("permissions", "permissions grant");
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        showPermissionRationaleMessage(token);

                    }

                }).check();
    }

    private void showPermissionRationaleMessage(final PermissionToken token) {

        showMessage(R.string.permissions_needed, new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                token.continuePermissionRequest();
                dialog.dismiss();
            }
        }, new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                token.cancelPermissionRequest();
                dialog.dismiss();
            }
        });
    }

    private void selectImage() {

        TedBottomPicker tedBottomPicker = new TedBottomPicker.Builder(getBaseActivity())
                .setOnImageSelectedListener(new TedBottomPicker.OnImageSelectedListener() {
                    @Override
                    public void onImageSelected(final Uri uri) {
                        try {
                            File imageZipperFile;
                            if (uri.toString().contains("content")) {
                                imageZipperFile = new ImageZipper(getBaseActivity())
                                        .setQuality(30)
                                        .setMaxWidth(200)
                                        .setMaxHeight(200)
                                        .compressToFile(new File(getRealPathFromURI(uri)));


                            /*    Picasso.with(getBaseActivity())
                                        .load(Uri.fromFile(imageZipperFile))
                                        .into(imgUserEditProfileUpdateImage);
*/
                                profileImage = prepareFilePart("photo", Uri.fromFile(imageZipperFile));
                                callUploadPhoto(profileImage);
                                Log.d("test", "dhghjkl");
                            } else {
                                imageZipperFile = new ImageZipper(getBaseActivity())
                                        .setQuality(30)
                                        .setMaxWidth(200)
                                        .setMaxHeight(200)
                                        .compressToFile(new File(uri.getPath()));


                             /*   Picasso.with(getBaseActivity())
                                        .load(Uri.fromFile(imageZipperFile))
                                        .into(imgUserEditProfileUpdateImage);
*/
                                profileImage = prepareFilePart("photo", Uri.fromFile(imageZipperFile));
                                callUploadPhoto(profileImage);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                })
//                .showGalleryTile(false)
                .create();

        tedBottomPicker.show(getChildFragmentManager());

    }

    public String getRealPathFromURI(Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = getBaseActivity().getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    private MultipartBody.Part prepareFilePart(String partName, Uri fileUri) {

        File file = new File(getRealPathFromUri(fileUri));
        // create RequestBody instance from file
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        // MultipartBody.Part is used to send also the actual file name
        return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
    }

    private String getRealPathFromUri(final Uri uri) {
        // DocumentProvider
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && DocumentsContract.isDocumentUri(getBaseActivity(), uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(getBaseActivity(), contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(getBaseActivity(), contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(getBaseActivity(), uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    private String getDataColumn(Context context, Uri uri, String selection,
                                 String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    private boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    private boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    private boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    private boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    //endregion
}
