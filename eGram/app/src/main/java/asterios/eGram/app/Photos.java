package asterios.eGram.app;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * Created by asterios on 12/5/2015.
 */
public class Photos {

    public static Bitmap getCroppedBitmap(Bitmap bitmap) {
       /* Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        // canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        canvas.drawCircle(bitmap.getWidth()/2 , bitmap.getHeight()/2 ,
                bitmap.getWidth() , paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        */

       // bitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth()/4, bitmap.getHeight()/4, false);

        int targetWidth = 125;
        int targetHeight = 125;
        Bitmap targetBitmap = Bitmap.createBitmap(targetWidth,
                targetHeight,Bitmap.Config.ARGB_8888);


        //To enhance the quality
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);


        Canvas canvas = new Canvas(targetBitmap);

        Path path = new Path();
        path.addCircle(((float) targetWidth - 1) / 2,
                ((float) targetHeight - 1) / 2,
                (Math.min(((float) targetWidth),
                        ((float) targetHeight)) / 2),
                Path.Direction.CCW);

        canvas.clipPath(path);
        Bitmap sourceBitmap = bitmap;
        canvas.drawBitmap(sourceBitmap,
                new Rect(0, 0, sourceBitmap.getWidth(),
                        sourceBitmap.getHeight()),
                new Rect(0, 0, targetWidth,
                        targetHeight), paint);
       // Bitmap _bmp = Bitmap.createScaledBitmap(targetBitmap, 125, 125, false);
      //  return _bmp;
        return targetBitmap;

    }
    public static String saveToInternalStorage(Bitmap bitmapImage, Activity act){
        ContextWrapper cw = new ContextWrapper(act);

        // path to /data/data/egam/app_profilePhotos
        File directory = cw.getDir("profilePhotos", Context.MODE_PRIVATE);

        // Create imageDir
        File mypath=new File(directory,"profile.png");

        FileOutputStream fos = null;
        try {

            fos = new FileOutputStream(mypath);

            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return directory.getAbsolutePath();
    }

    public static void renameProfileImageFile(String NewName, Activity act){
        ContextWrapper cw = new ContextWrapper(act);

        // path to /data/data/egam/app_profilePhotos
        File directory = cw.getDir("profilePhotos", Context.MODE_PRIVATE);

        File from = new File(directory, "profile.png");
        File to   = new File(directory, NewName + ".png");
        from.renameTo(to);

    }

    // Decodes image and scales it to reduce memory consumption
    public static Bitmap decodeFile(File f) {
        try {
            // Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(f), null, o);

            // The new size we want to scale to
            final int REQUIRED_SIZE=70;

            // Find the correct scale value. It should be the power of 2.
            int scale = 1;
            while(o.outWidth / scale / 2 >= REQUIRED_SIZE &&
                    o.outHeight / scale / 2 >= REQUIRED_SIZE) {
                scale *= 2;
            }

            // Decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
        } catch (FileNotFoundException e) {}
        return null;
    }

    public static String getDeviceAbsPath(Activity act){
        ContextWrapper cw = new ContextWrapper(act);

        // path to /data/data/egam/app_profilePhotos
        File directory = cw.getDir("profilePhotos", Context.MODE_PRIVATE);


        return directory.getAbsolutePath();
    }

    public static Boolean ProfileFileExists(String path) {

        File file = new File(path);
        if(file.exists()) return true;
        else return false;
    }


    public static void DeleteProfilePicture(String path) {

        File file = new File(path);
        boolean deleted = file.delete();
    }

}
