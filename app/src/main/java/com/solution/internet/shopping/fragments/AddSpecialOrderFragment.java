package com.solution.internet.shopping.fragments;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

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
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.solution.internet.shopping.R;
import com.solution.internet.shopping.interfaces.HandleRetrofitResp;
import com.solution.internet.shopping.models.ModelGetCities.ModelGetCities;
import com.solution.internet.shopping.models.ModelSignUpRequest.ModelSignUpRequest;
import com.solution.internet.shopping.retorfitconfig.HandleCalls;
import com.solution.internet.shopping.utlities.DataEnum;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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

public class AddSpecialOrderFragment extends BaseFragment implements HandleRetrofitResp, Validator.ValidationListener
{
    //region fields
    int selectedCityId = 0;
    Validator validator;
    List<ModelGetCities> modelGetCitiesList;
    List<String> citiesName;
    MultipartBody.Part profileImage;

    //endregion

    //region views
    @NotEmpty(messageResId = R.string.required)
    @BindView(R.id.edtSpecialOrderAddress)
    EditText edtSpecialOrderAddress;

    @NotEmpty(messageResId = R.string.required)
    @BindView(R.id.edtSpecialOrderCity)
    EditText edtSpecialOrderCity;

    @NotEmpty(messageResId = R.string.required)
    @BindView(R.id.tvAddProductContent)
    EditText tvAddProductContent;

    @NotEmpty(messageResId = R.string.required)
    @BindView(R.id.edtSpecialOrderPrice)
    EditText edtSpecialOrderPrice;

    @BindView(R.id.imgAddSpecialOrder)
    ImageView imgAddSpecialOrder;

    //endregion

    //region life cycle

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        super.onCreateView(inflater, container, savedInstanceState);
        final View view = inflater.inflate(R.layout.special_order, container, false);

        unbinder = ButterKnife.bind(this, view);

        edtSpecialOrderCity.setFocusable(false);
        edtSpecialOrderCity.setClickable(true);

        HandleCalls.getInstance(getBaseActivity()).setonRespnseSucess(this);
        callCities();

        return view;
    }

    @Override
    public void onResume()
    {
        super.onResume();
        HandleCalls.getInstance(getBaseActivity()).setonRespnseSucess(this);

        validator = new Validator(this);
        validator.setValidationListener(this);
    }

    @Override
    public void onStop()
    {
        super.onStop();
//        appHeader.setRight(0, 0, 0);
    }

    //endregion

    //region parent methods
    @Override
    protected boolean canShowAppHeader()
    {
        return false;
    }

    @Override
    protected boolean canShowBottomBar()
    {
        return false;
    }

    @Override
    protected boolean canShowBackArrow()
    {
        return false;
    }

    @Override
    protected String getTitle()
    {
        return null;
    }

    @Override
    public int getSelectedMenuId()
    {
        return 0;
    }

    //endregion

    //region calls response
    @Override
    public void onResponseSuccess(String flag, Object o)
    {
        Gson gson = new Gson();

        if (flag.equals(DataEnum.callCities.name()))
        {
            JsonArray jsonArray = gson.toJsonTree(o).getAsJsonArray();
            modelGetCitiesList = new ArrayList<>();
            citiesName = new ArrayList<>();
            for (int i = 0; i < jsonArray.size(); i++)
            {
                ModelGetCities modelGetCity = gson.fromJson(jsonArray.get(i).getAsJsonObject(), ModelGetCities.class);
                modelGetCitiesList.add(modelGetCity);
                citiesName.add(modelGetCity.getCity());
            }

        }
    }

    @Override
    public void onNoContent(String flag, int code)
    {
        getBaseActivity().onBackPressed();
    }

    @Override
    public void onResponseSuccess(String flag, Object o, int position)
    {

    }

    //endregion

    //region clicks

    @OnClick(R.id.edtSpecialOrderCity)
    public void onClickedtRegisterCity()
    {

        final CharSequence[] items = new CharSequence[citiesName.size()];

        for (int i = 0; i < citiesName.size(); i++)
        {
            items[i] = citiesName.get(i);
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getBaseActivity());
        builder.setTitle("أختر مدينة");
        builder.setItems(items, new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface dialog, int item)
            {
                // Do something with the selection
                edtSpecialOrderCity.setText(citiesName.get(item));
                selectedCityId = modelGetCitiesList.get(item).getCityId();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @OnClick(R.id.tvAddPhoto)
    void onClicktvUserEditProfileUpdateImage(View view)
    {
        if (ContextCompat
                .checkSelfPermission(getBaseActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager
                .PERMISSION_GRANTED && ContextCompat
                .checkSelfPermission(getBaseActivity(), Manifest.permission.CAMERA) != PackageManager
                .PERMISSION_GRANTED && ContextCompat
                .checkSelfPermission(getBaseActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager
                .PERMISSION_GRANTED)
        {

            requestStorageAndCameraPermission();
            return;
        } else
        {
            selectImage();
        }
    }

    @OnClick(R.id.btnSendRequest)
    void onClickbtnSendRequest(View view)
    {
        validator.validate();
    }
    //endregion

    //region calls
    private void callCities()
    {

        Call call = HandleCalls.restShopping.getClientService().callCities();
        HandleCalls.getInstance(getBaseActivity()).callRetrofit(call, DataEnum.callCities.name(), true);
    }

    private void callPrivateOrderNew()
    {
        HashMap<String, RequestBody> meMap = new HashMap<String, RequestBody>();
        meMap.put("title", createPartFromString(edtSpecialOrderAddress.getText().toString()));
        meMap.put("content", createPartFromString(tvAddProductContent.getText().toString()));
        meMap.put("price", createPartFromString(edtSpecialOrderPrice.getText().toString()));
        meMap.put("cityid", createPartFromString(edtSpecialOrderCity.getText().toString()));

        Call call = HandleCalls.restShopping.getClientService().callPrivateOrderNew(profileImage, meMap);
        HandleCalls.getInstance(getBaseActivity()).callRetrofit(call, DataEnum.callPrivateOrderNew.name(), true);
    }
    //endregion

    //region functions

    public static AddSpecialOrderFragment init()
    {
        return new AddSpecialOrderFragment();
    }

    @NonNull
    private RequestBody createPartFromString(String description)
    {
        return RequestBody.create(MultipartBody.FORM, description);
    }
    //endregion

    //region validation


    @Override
    public void onValidationSucceeded()
    {
        if (profileImage != null)
            callPrivateOrderNew();
        else
            showMessage("يجب اختار صورة");
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors)
    {
        for (ValidationError error : errors)
        {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(getBaseActivity());

            // Display error messages ;)
            if (view instanceof EditText)
            {
                ((EditText) view).setError(message);
            } else
            {
//                showMessage(message);
            }
        }
    }


    //endregion

    //region images
    private void requestStorageAndCameraPermission()
    {
        Dexter.withActivity(getBaseActivity())
                .withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener()
                {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report)
                    {
                        if (report.areAllPermissionsGranted())
                        {
//                            chooseImage();
                            selectImage();
                        } else if (report.isAnyPermissionPermanentlyDenied())
                        {
//                            showMessage(R.string.please_grant_permissions);
                            Log.d("permissions", "permissions grant");
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token)
                    {
                        showPermissionRationaleMessage(token);

                    }

                }).check();
    }

    private void showPermissionRationaleMessage(final PermissionToken token)
    {

        showMessage(R.string.permissions_needed, new MaterialDialog.SingleButtonCallback()
        {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which)
            {
                token.continuePermissionRequest();
                dialog.dismiss();
            }
        }, new MaterialDialog.SingleButtonCallback()
        {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which)
            {
                token.cancelPermissionRequest();
                dialog.dismiss();
            }
        });
    }

    private void selectImage()
    {
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        TedBottomPicker tedBottomPicker = new TedBottomPicker.Builder(getBaseActivity())
                .setOnImageSelectedListener(new TedBottomPicker.OnImageSelectedListener()
                {
                    @Override
                    public void onImageSelected(final Uri uri)
                    {
                        try
                        {
                            File imageZipperFile;
                            if (uri.toString().contains("content"))
                            {
                                imageZipperFile = new ImageZipper(getBaseActivity())
                                        .setQuality(30)
                                        .setMaxWidth(200)
                                        .setMaxHeight(200)
                                        .compressToFile(new File(getRealPathFromURI(uri)));


                                Picasso.with(getBaseActivity())
                                        .load(Uri.fromFile(imageZipperFile))
                                        .into(imgAddSpecialOrder);

                                profileImage = prepareFilePart("File", Uri.fromFile(imageZipperFile));
//                                callUpdateProfilePhoto(profileImage);
                                Log.d("test", "dhghjkl");
                            } else
                            {
                                imageZipperFile = new ImageZipper(getBaseActivity())
                                        .setQuality(30)
                                        .setMaxWidth(200)
                                        .setMaxHeight(200)
                                        .compressToFile(new File(uri.getPath()));


                                Picasso.with(getBaseActivity())
                                        .load(Uri.fromFile(imageZipperFile))
                                        .into(imgAddSpecialOrder);

                                profileImage = prepareFilePart("File", Uri.fromFile(imageZipperFile));
//                                callUpdateProfilePhoto(profileImage);
                            }
                        } catch (IOException e)
                        {
                            e.printStackTrace();
                        }

                    }
                })
//                .showGalleryTile(false)
                .create();

        tedBottomPicker.show(getChildFragmentManager());

    }


    public String getRealPathFromURI(Uri contentUri)
    {
        Cursor cursor = null;
        try
        {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = getBaseActivity().getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally
        {
            if (cursor != null)
            {
                cursor.close();
            }
        }
    }


    private MultipartBody.Part prepareFilePart(String partName, Uri fileUri)
    {

        File file = new File(getRealPathFromUri(fileUri));
        // create RequestBody instance from file
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        // MultipartBody.Part is used to send also the actual file name
        return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
    }

    private String getRealPathFromUri(final Uri uri)
    {
        // DocumentProvider
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && DocumentsContract.isDocumentUri(getBaseActivity(), uri))
        {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri))
            {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type))
                {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri))
            {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(getBaseActivity(), contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri))
            {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type))
                {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type))
                {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type))
                {
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
        else if ("content".equalsIgnoreCase(uri.getScheme()))
        {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(getBaseActivity(), uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme()))
        {
            return uri.getPath();
        }

        return null;
    }

    private String getDataColumn(Context context, Uri uri, String selection,
                                 String[] selectionArgs)
    {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try
        {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst())
            {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally
        {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    private boolean isExternalStorageDocument(Uri uri)
    {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    private boolean isDownloadsDocument(Uri uri)
    {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    private boolean isMediaDocument(Uri uri)
    {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    private boolean isGooglePhotosUri(Uri uri)
    {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    //endregion

}
