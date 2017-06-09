package com.ly.justsoso.enjoypictures.data.remote;

import com.alibaba.fastjson.JSON;
import com.ly.framework.mvp.BaseDataSource;
import com.ly.justsoso.JustSoSoApplication;
import com.ly.justsoso.enjoypictures.bean.PictureData;
import com.ly.justsoso.enjoypictures.bean.Pictures;
import com.ly.justsoso.greendao.PictureDataDao;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PicturesRepository {

	final static String []URLS = {
		"http://bizhi.4493.com/uploads/allimg/141010/4-141010150301.jpg",
		"http://www.benbenla.cn/images/20120330/benbenla-04b.jpg",
		"http://www.deskcar.com/desktop/fengjing/200895150214/21.jpg",
		"http://image.tuwang.com/Nature/FengGuang-1600-1200/FengGuang_pic_abx@DaTuKu.org.jpg",
		"http://5.26923.com/download/pic/000/245/718dfc8322abe39627591e4a495767af.jpg",
		"http://image.tianjimedia.com/uploadImages/2011/286/8X76S7XD89VU.jpg",
		"http://tupian.enterdesk.com/2013/mxy/12/10/15/3.jpg",
		"http://pic4.nipic.com/20091215/2396136_140959028451_2.jpg",
		"http://pic1.nipic.com/2008-10-30/200810309416546_2.jpg",
		"http://pic24.nipic.com/20121003/10754047_140022530392_2.jpg",
		"http://img01.taopic.com/141128/240418-14112Q04Q824.jpg",
		"http://img4.duitang.com/uploads/item/201210/04/20121004233016_3vZPJ.thumb.600_0.jpeg",
		"http://tupian.enterdesk.com/2014/lxy/2014/05/07/2/8.jpg",
		"http://img3.imgtn.bdimg.com/it/u=1003865389,4203869868&fm=21&gp=0.jpg",
		"http://www.6188.com/upload_6188s/flashAll/s800/20120820/1345424484cUkL39.jpg",
		"http://pic51.nipic.com/file/20141030/2531170_080422201000_2.jpg",
		"http://d.hiphotos.baidu.com/zhidao/pic/item/3b87e950352ac65c1b6a0042f9f2b21193138a97.jpg",
		"http://pic1.nipic.com/2008-10-20/20081020104257376_2.jpg",
		"http://www.bz55.com/uploads/allimg/110109/1_110109152305_2.jpg",
		"http://s.tang-mao.com/Uploads/Editor/2015-05-29/1432892469570136.jpg",
		"http://image.tianjimedia.com/uploadImages/2011/360/89241H21087M.jpg",
		"http://s9.knowsky.com/bizhi/l/1-5000/200952814217740135370.jpg",
		"http://img2.imgtn.bdimg.com/it/u=1148262905,628831731&fm=11&gp=0.jpg",
		"http://img5.imgtn.bdimg.com/it/u=2347600722,3269434251&fm=11&gp=0.jpg",
		"http://pic41.nipic.com/20140511/14735567_151117433136_2.jpg",
		"http://pic44.nipic.com/20140725/19158069_112814747000_2.jpg",
		"http://images.ali213.net/picfile/pic/2013/03/08/927_zg%20%288%29.jpg",
		"http://img5.imgtn.bdimg.com/it/u=2130316334,2209566559&fm=21&gp=0.jpg",
		"http://pic24.nipic.com/20121015/8964182_093906338150_2.jpg",
		"http://image.tianjimedia.com/uploadImages/2012/236/2H2TR02NKWAA.jpg",
		"http://pic1.nipic.com/2008-11-13/2008111384358912_2.jpg",
		"http://pic4.nipic.com/20091031/1624052_102919201523_2.jpg",
		"http://img0.imgtn.bdimg.com/it/u=2013819174,1049816796&fm=21&gp=0.jpg",
		"http://img.taopic.com/uploads/allimg/130807/240451-130PFI24945.jpg",
		"http://pic6.nipic.com/20100331/1850638_091848691704_2.jpg",
		"http://img6.faloo.com/Picture/0x0/0/747/747468.jpg",
		"http://dl.bizhi.sogou.com/images/2013/11/07/401031.jpg?f=download",
		"http://img2.zol.com.cn/product/66/48/ceXhVA67Kb7Q6.jpg",
		"http://pic24.nipic.com/20121009/4744012_103328385000_2.jpg",
		"http://img.taopic.com/uploads/allimg/130215/240511-13021510291714.jpg",
		"http://android-wallpapers.25pp.com/20140206/1602/52f3419a01e237_900x675.jpg",
		"http://pic49.nipic.com/file/20140919/19440961_104117884000_2.jpg",
		"http://www.wallcoo.com/nature/Magic_Landscapes_by_Michael_Breitung/wallpapers/1680x1050/quiraingview.jpg",
		"http://pic21.nipic.com/20120524/2786001_094511692000_2.jpg",
		"http://pic25.nipic.com/20121205/8952533_175747684166_2.jpg",
		"http://pic4.nipic.com/20090812/2990649_223718021_2.jpg",
		"http://pic9.nipic.com/20100915/2531170_081545298923_2.jpg",
		"http://pic1a.nipic.com/2008-10-15/2008101593224188_2.jpg",
		"http://tupian.enterdesk.com/2012/0611/gha/5/11721786_1337762886789.jpg",
		"http://www.sucaitianxia.com/Photo/pic/201001/gefnegs37.jpg",
		"http://pic8.nipic.com/20100716/5346063_095935004841_2.jpg",
		"http://img01.taopic.com/151223/318762-15122309113319.jpg",
		"http://pic31.nipic.com/20130731/11346320_100517707100_2.jpg",
		"http://pic3.nipic.com/20090619/1242397_175120081_2.jpg",
		"http://g.hiphotos.baidu.com/baike/pic/item/5bafa40f4bfbfbedbdfcde2a78f0f736afc31f39.jpg",
		"http://img.taopic.com/uploads/allimg/121009/240425-12100914242120.jpg",
		"http://img.taopic.com/uploads/allimg/100605/56-1006050353220.jpg",
		"http://pic4.nipic.com/20091121/3415797_091522032732_2.jpg",
		"http://pic23.nipic.com/20120817/9937380_184342290113_2.jpg",
		"http://img.taopic.com/uploads/allimg/140713/318752-140G311132474.jpg",
		"http://www.aomy.com/attach/2011-09/1316481292AqMS.jpg",
		"http://pic23.nipic.com/20120916/5523833_082620404000_2.jpg",
		"http://img01.taopic.com/150116/240448-15011610051136.jpg",
		"http://img3.imgtn.bdimg.com/it/u=3354692930,375099139&fm=21&gp=0.jpg",
		"http://pic4.nipic.com/20090922/3447615_162312075205_2.jpg",
		"http://pic45.nipic.com/20140811/2531170_094707626000_2.jpg",
		"http://pic24.nipic.com/20121018/5955207_185519481000_2.jpg",
		"http://pic60.nipic.com/file/20150201/3302065_230925882266_2.jpg",
		"http://pic10.nipic.com/20101102/5902159_192814905000_2.jpg",
		"http://img.daimg.com/uploads/allimg/141023/3-141023161035.jpg",
		"http://pic24.nipic.com/20121026/10890734_151559576115_2.jpg",
		"http://pic22.nipic.com/20120626/10408639_002551318000_2.jpg",
		"http://pic25.nipic.com/20121201/11457640_135559680000_2.jpg",
		"http://img102.mypsd.com.cn/20121211/1/Mypsd_22905_201212110918100000B.jpg",
		"http://pic49.nipic.com/file/20140926/17961491_094725010000_2.jpg",
		"http://img101.mypsd.com.cn/20120519/1/Mypsd_176969_201205190931510091B.jpg",
		"http://pic27.nipic.com/20130319/11437621_104535227111_2.jpg",
		"http://pic22.nipic.com/20120726/9975192_111531751000_2.jpg",
		"http://image.tianjimedia.com/uploadImages/2012/011/R5J8A0HYL5YV.jpg",
		"http://pic1.nipic.com/2008-10-15/2008101591546842_2.jpg",
		"http://img101.mypsd.com.cn/20120517/3/mypsd_176980_201205171642320010b.jpg",
		"http://img2.imgtn.bdimg.com/it/u=1078603333,1952302645&fm=21&gp=0.jpg",
		"http://pic20.nipic.com/20120411/5137861_094202572191_2.jpg",
		"http://img3.redocn.com/tupian/20141205/meilidehumian_3653097.jpg",
		"http://f.hiphotos.baidu.com/zhidao/pic/item/b21bb051f8198618a323ac464bed2e738ad4e688.jpg",
		"http://pic76.nipic.com/file/20150824/21321671_165321269000_2.jpg",
		"http://img01.taopic.com/140917/235091-14091FK02078.jpg",
		"http://dl.bizhi.sogou.com/images/2013/08/05/354996.jpg?f=download",
		"http://img1.imgtn.bdimg.com/it/u=1418250944,3854931501&fm=21&gp=0.jpg",
		"http://pic20.nipic.com/20120428/9549520_140731300118_2.jpg",
		"http://img2.3lian.com/2014/f2/132/d/1.jpg",
		"http://pic3.nipic.com/20090611/2317247_113950099_2.jpg",
		"http://pic40.nipic.com/20140422/18503508_205330664178_2.jpg",
		"http://image.tianjimedia.com/uploadImages/2012/236/HTT4ZH0G1S84.jpg",
		"http://pic21.nipic.com/20120611/2207950_135234957000_2.jpg",
		"http://pic27.nipic.com/20130325/10509813_193715651188_2.jpg",
		"http://pic26.nipic.com/20121203/8170729_215609012000_2.jpg",
		"http://5.26923.com/download/pic/000/324/ecf1dc23f131ef31e1b53b7e806a700c.jpg",
		"http://online.sccnn.com/desk2/1486/3SFG_1026.jpg"		
	};
	
	BaseDataSource.DataLoadCallback<Pictures> callback;
	final static String urlString = "http://image.baidu.com/search/flip?tn=baiduimage&ie=utf-8";
	
	final static String tnKey = "tn";
	final static String ieKey = "ie";
	final static String wordKeyString = "word";
	final static String pnKeyString = "pn";
	final static String gsmKey = "gsm";
	
	final static String tnString = "baiduimage";
	final static String ieString = "utf-8";
	final static String MATCH_IMAGE_DATA_START = "flip.setData('imgData',";
	final static String MATCH_IMAGE_DATA_END = ");";
	
	final long gsm = 900000050L;	

	public PicturesRepository(BaseDataSource.DataLoadCallback<Pictures> callback){
		this.callback = callback;
	}

	private ArrayList<PictureData> getAllPictures(){
		
		ArrayList<PictureData> allPictures = new ArrayList<>();
		for(int i = 0;i < URLS.length;i ++){
			PictureData pictureData = new PictureData();
			pictureData.setThumbURL(URLS[i]);
			pictureData.setObjURL(URLS[i]);
			allPictures.add(pictureData);
		}
		return allPictures == null ? new ArrayList<PictureData>() : allPictures ;
	}
	
	public void getAllPictures(String keyword,String pn,final int counts){
		


		StringBuilder sBuilder = new StringBuilder(urlString);
		sBuilder.append("&" + wordKeyString + "=" + keyword);
		sBuilder.append("&" + pnKeyString + "=" + pn);
		System.out.println(sBuilder.toString());

		OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
		Request request = new Request.Builder().url(sBuilder.toString()).build();
		Call call = okHttpClient.newCall(request);
		call.enqueue(new Callback() {
			@Override
			public void onFailure(Call call, IOException e) {
				ArrayList<PictureData> allPictures = getAllPictures();
				Pictures pictures = new Pictures();
				pictures.setPictures(allPictures);
				pictures.setRealCounts(allPictures.size());
				callback.onDataLoadComplete(pictures);
			}

			@Override
			public void onResponse(Call call, Response response) throws IOException {
				String lines = response.body().string();
				int realResponsCounts;
				int start = lines.indexOf(MATCH_IMAGE_DATA_START);
				int end = lines.indexOf(MATCH_IMAGE_DATA_END, start);
				lines = lines.substring(start, end + MATCH_IMAGE_DATA_END.length());
				start = MATCH_IMAGE_DATA_START.length();
				lines = lines.substring(start, lines.length() - 2).trim();
				ImageWrapper imageWrapper = JSON.parseObject(lines,ImageWrapper.class);

				ArrayList<ImageData> datas = imageWrapper.getData();
				realResponsCounts = datas.size();
				int count = counts;
				if(datas.size() < count){
					count = datas.size();
				}
				ArrayList<PictureData> allPictures = new ArrayList<>();
				for (int i = 0; i < count; i++) {
					PictureData pictureData = new PictureData();
					ImageData imgData = datas.get(i);
					if (imgData == null) {
						continue;
					}
					pictureData.setThumbURL(imgData.getThumbURL());
					pictureData.setObjURL(imgData.getObjURL());
					allPictures.add(pictureData);
				}

                PictureDataDao dao = JustSoSoApplication.getInstance().getDaoSession().getPictureDataDao();
                dao.insertInTx(allPictures);

				Pictures pictures = new Pictures();
				pictures.setPictures(allPictures);
				pictures.setRealCounts(realResponsCounts);
				callback.onDataLoadComplete(pictures);
			}
		});
	}


}
