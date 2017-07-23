package com.ly.justsoso.sample.ui.detail;

import android.content.Context;

import com.ly.justsoso.sample.adapter.card.SampleCardId;
import com.ly.justsoso.sample.adapter.item.SampleItem;

/**
 * Created by ly on 2017/7/22.
 */

final public class SampleDetailViewFactory {

    public static AbstractDetailView generate(Context context,SampleItem sampleItem) {

        AbstractDetailView detailView = null;
        int id = sampleItem.cardId;
        if(id == SampleCardId.CARD_ID_FLOW_LAYOUT) {
            detailView = new SampleFlowView(context);
        }else if(id == SampleCardId.CARD_ID_VIDEOVIEW) {
            detailView = new SampleVideoView(context);
        }else if(id == SampleCardId.CARD_ID_SIMPLE_COLOR_MATRIX) {
            detailView = new SampleSimpleColorMatrixView(context);
        }else if(id == SampleCardId.CARD_ID_COLOR_MATRIX) {
            detailView = new SampleColorMatrixView(context);
        }

        return detailView;
    }
}
