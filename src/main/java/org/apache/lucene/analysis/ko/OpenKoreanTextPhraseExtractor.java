package org.apache.lucene.analysis.ko;

import org.apache.lucene.analysis.TokenStream;
import org.openkoreantext.processor.OpenKoreanTextProcessor;
import scala.collection.Iterator;
import scala.collection.JavaConverters;
import scala.collection.Seq;

import java.util.Arrays;

import static org.openkoreantext.processor.phrase_extractor.KoreanPhraseExtractor.KoreanPhrase;
import static org.openkoreantext.processor.tokenizer.KoreanTokenizer.KoreanToken;

public class OpenKoreanTextPhraseExtractor extends OpenKoreanTextTokenFilter {

    public OpenKoreanTextPhraseExtractor(TokenStream input) {
        super(input);
    }

    @Override
    protected Seq<KoreanToken> perform(Seq<KoreanToken> tokens) {
        Seq<KoreanPhrase> phrases = OpenKoreanTextProcessor.extractPhrases(tokens, false, true);
        return convertPhrasesToTokens(phrases);
    }

    private Seq<KoreanToken> convertPhrasesToTokens(Seq<KoreanPhrase> phrases) {
        KoreanToken[] tokens = new KoreanToken[phrases.length()];

        Iterator<KoreanPhrase> iterator = phrases.iterator();
        int i = 0;
        while (iterator.hasNext()) {
            KoreanPhrase phrase = iterator.next();
            tokens[i++] = new KoreanToken(phrase.text(), phrase.pos(), phrase.offset(), phrase.length(), scala.Option.apply(null), false);
        }
        return JavaConverters.asScalaBuffer(Arrays.asList(tokens)).toSeq();
    }
}
