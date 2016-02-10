package com.salesianostriana.psp.sendfiles;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nononsenseapps.filepicker.FilePickerFragment;

import java.io.File;
import java.util.Arrays;

/**
 * A simple {@link Fragment} subclass.
 */
public class FilteredFilePickerFragment extends FilePickerFragment {


    // File extension to filter on
    private static final String[] EXTENSION = {".jpg", ".jpeg", ".gif", ".png"};

    /**
     *
     * @param file
     * @return The file extension. If file has no extension, it returns null.
     */
    private String getExtension(@NonNull File file) {
        String path = file.getPath();
        int i = path.lastIndexOf(".");
        if (i < 0) {
            return null;
        } else {
            return path.substring(i);
        }
    }

    @Override
    protected boolean isItemVisible(final File file) {
        boolean ret = super.isItemVisible(file);
        if (ret && !isDir(file) && (mode == MODE_FILE || mode == MODE_FILE_AND_DIR)) {
            //ret = EXTENSION.equalsIgnoreCase(getExtension(file));
            ret = Arrays.asList(EXTENSION).contains(getExtension(file).toLowerCase());
        }
        return ret;
    }

}
