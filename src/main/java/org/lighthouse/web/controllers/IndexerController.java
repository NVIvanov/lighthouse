package org.lighthouse.web.controllers;

import org.lighthouse.crawler.services.CrawlerExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import scala.collection.JavaConverters;

import java.util.Collections;

/**
 * @author nivanov
 * on 04.11.2017.
 */

@RestController
@RequestMapping("/api/indexer/")
public class IndexerController {
    private final CrawlerExecutor crawlerExecutor;

    @Autowired
    public IndexerController(CrawlerExecutor crawlerExecutor) {
        this.crawlerExecutor = crawlerExecutor;
    }

    @PostMapping("start")
    public void startIndexer(@RequestParam String countryName) {
        crawlerExecutor.execute(JavaConverters.asScalaBufferConverter(Collections.singletonList(countryName)).asScala().toSeq());
    }

    @GetMapping("status")
    public String getStatus() {
        return crawlerExecutor.status().toString();
    }
}
