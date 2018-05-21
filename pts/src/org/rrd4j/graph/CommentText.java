package org.rrd4j.graph;

import java.util.Locale;

import org.rrd4j.data.DataProcessor;

class CommentText implements RrdGraphConstants {
    protected final String text; // original text

    String resolvedText; // resolved text
    String marker; // end-of-text marker
    boolean enabled; // hrule and vrule comments can be disabled at runtime
    int x, y; // coordinates, evaluated in LegendComposer

    CommentText(String text) {
        this.text = text;
    }

    void resolveText(Locale l, DataProcessor dproc, ValueScaler valueScaler) {
        resolvedText = text;
        marker = "";
        if (resolvedText != null) {
            for (String someMarker : MARKERS) {
                if (resolvedText.endsWith(someMarker)) {
                    marker = someMarker;
                    resolvedText = resolvedText.substring(0, resolvedText.length() - marker.length());
                    trimIfGlue();
                    break;
                }
            }
        }
        enabled = resolvedText != null;
    }

    void trimIfGlue() {
        if (marker.equals(GLUE_MARKER)) {
            resolvedText = resolvedText.replaceFirst("\\s+$", "");
        }
    }

    boolean isPrint() {
        return false;
    }

    boolean isValidGraphElement() {
        return !isPrint() && enabled;
    }
}
