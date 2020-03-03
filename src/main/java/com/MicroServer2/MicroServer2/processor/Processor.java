package com.MicroServer2.MicroServer2.processor;

import com.MicroServer2.MicroServer2.dto.Anime;
import com.MicroServer2.MicroServer2.dto.AnimeOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

public class Processor implements ItemProcessor<Anime, AnimeOutput> {
    private static final Logger LOG = LoggerFactory.getLogger(Processor.class);

    @Override
    public AnimeOutput process(Anime item) throws Exception {
        String animeId = Integer.toString(item.getAnime_id());
        String name = item.getName();
        String rating = Double.toString(item.getRating());
        String type = item.getType();
        String source = item.getSource();
        LOG.info("-" + animeId + "," + name + "," + rating + "," + type + "");
        return new AnimeOutput(animeId, name, rating, type, source);
    }

}
