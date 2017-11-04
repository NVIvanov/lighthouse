package org.lighthouse.export.factory.impl;

import org.lighthouse.domain.entities.Settings;
import org.lighthouse.export.factory.ExportStrategyFactory;
import org.lighthouse.export.strategies.ExportStrategy;
import org.lighthouse.export.strategies.impl.LocalExportStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author nivanov
 * on 02.11.2017.
 */

@Component
public class DefaultExportStrategyFactory implements ExportStrategyFactory{

    private final LocalExportStrategy localExportStrategy;

    @Autowired
    public DefaultExportStrategyFactory(LocalExportStrategy localExportStrategy) {
        this.localExportStrategy = localExportStrategy;
    }

    @Override
    public ExportStrategy strategyByType(Settings.ExportType exportType) {
        return localExportStrategy;
    }
}
