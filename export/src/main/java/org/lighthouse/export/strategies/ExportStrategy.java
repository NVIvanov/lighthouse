package org.lighthouse.export.strategies;

/**
 * @author nivanov
 * on 02.11.2017.
 */
public interface ExportStrategy {
    void exportFile(String content, Integer year, String country);
}
