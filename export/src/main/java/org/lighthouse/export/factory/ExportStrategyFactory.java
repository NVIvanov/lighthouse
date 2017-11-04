package org.lighthouse.export.factory;

import org.lighthouse.domain.entities.Settings;
import org.lighthouse.export.strategies.ExportStrategy;

/**
 * @author nivanov
 * on 02.11.2017.
 */
public interface ExportStrategyFactory {
    ExportStrategy strategyByType(Settings.ExportType exportType);
}
