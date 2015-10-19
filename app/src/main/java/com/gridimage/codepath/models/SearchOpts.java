package com.gridimage.codepath.models;

import java.io.Serializable;

/**
 * Created by mkrish4 on 10/16/15.
 */
public class SearchOpts implements Serializable {
    public String imageSize;
    public String color;
    public String type;
    public String site;
    public int startRow = 0;
}
