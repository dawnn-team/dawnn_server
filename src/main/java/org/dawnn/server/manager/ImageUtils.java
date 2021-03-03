package org.dawnn.server.manager;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.lang.GeoLocation;
import com.drew.metadata.Metadata;
import lombok.NonNull;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class ImageUtils {

    // Might not need this class depending on how explicit
    // We make the location of the image.

    /**
     * Find the metadata of an image. This uses metadata-extractor to
     * find data from images.
     *
     * @param file The input image.
     * @return The image data, null if none.
     */
    public static Metadata findImageImageData(@NonNull File file) {
        try {
            return ImageMetadataReader.readMetadata(file);
        } catch (ImageProcessingException | IOException ignore) {
        }

        return null;
    }

    /**
     * Convert an image to a base64 string, first reading it into a {@link BufferedImage}
     * with {@link ImageIO#read(URL)}, and then writing it into a {@link ByteArrayOutputStream}.
     *
     * @param image The image to convert.
     * @return A base64 string of the image, or null if conversion failed.
     */
    public static String toBase64(@NonNull File image) {
        try {
            BufferedImage bufferedImage = ImageIO.read(image);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "jpg", outputStream);
            return Arrays.toString(outputStream.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Convert a base64 String to an image. First get the bytes from base64,
     * then read byte array into an {@link BufferedImage} using {@link ImageIO#read(URL)}.
     *
     * @param base64 The base 64 String to convert.
     * @return An image equivalent of the provided String, null if conversion fails.
     */
    public static Image fromBase64(@NonNull String base64) {
        try {
            byte[] bytes = base64.getBytes(StandardCharsets.UTF_8);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
            return ImageIO.read(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Find the {@link GeoLocation} of an image {@link Image}
     *
     * @param image The image to find the location of.
     * @return The whatever geolocation that was found, null if none.
     */
    public static GeoLocation findLocation(Image image) {
        // TODO Finish this.
        return null;
    }

    /**
     * Find the {@link GeoLocation} of an image {@link Image}
     *
     * @param string64 The string64 interpretation of an image
     * @return The whatever geoLocation that was found, null if none.
     */
    public static GeoLocation findLocation(String string64) {
        // TODO Finish this.
        return null;
    }

}
