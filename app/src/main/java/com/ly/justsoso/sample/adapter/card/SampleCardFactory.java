package com.ly.justsoso.sample.adapter.card;

import android.util.SparseArray;

import com.ly.justsoso.sample.adapter.item.SampleItem;

import java.lang.ref.SoftReference;

/**
 * Created by ly on 2017/7/2.
 */

public class SampleCardFactory {

    private SparseArray<SoftReference<AbstractSampleCard>> mSampleCards = new SparseArray<>();

    public AbstractSampleCard generate(SampleItem sampleItem) {

        SoftReference<AbstractSampleCard> cardSoftReference = mSampleCards.get(sampleItem.itemStyle);
        if(cardSoftReference != null && cardSoftReference.get() != null) {
            return cardSoftReference.get();
        }


        switch (sampleItem.itemStyle) {
            case SampleCardStyleDef.CARD_STYLE_UI_XPROGRESS_BAR:
                AbstractSampleCard sampleCard = new XProgressBarCard();
                cardSoftReference = new SoftReference<>(sampleCard);
                break;
            default:
                return null;
        }
        mSampleCards.put(sampleItem.itemStyle,cardSoftReference);
        return cardSoftReference.get();
    }
}
