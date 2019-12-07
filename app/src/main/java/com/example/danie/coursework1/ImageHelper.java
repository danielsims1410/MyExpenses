package com.example.danie.coursework1;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.io.ByteArrayOutputStream;

public class ImageHelper
{

    public static byte[] getBytes(Bitmap bitmap) //Bitmap->Byte Array Conversion
    {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);
        return stream.toByteArray();
    }

    public static Bitmap getImage(byte[] image)  //Byte Array->Bitmap Conversion
    {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

}
