package com.example.voicedemo;

import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;
import com.iflytek.cloud.SynthesizerListener;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {

	private SpeechRecognizer mIat;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		SpeechUtility.createUtility(this, SpeechConstant.APPID + "=5cfde573");

	}

	public void startread(View view) {
		SpeechSynthesizer mTts = SpeechSynthesizer.createSynthesizer(MainActivity.this, null);
		
		// 设置返回结果格式，目前支持json,xml以及plain 三种格式，其中plain为纯听写文本内容
		mTts.setParameter(SpeechConstant.RESULT_TYPE, "json");
		// 此处engineType为“cloud”
		mTts.setParameter(SpeechConstant.ENGINE_TYPE, "cloud");

//		if (SpeechConstant.TYPE_LOCAL.equals("json") && SpeechConstant.MODE_MSC.equals("cloud")) {
//			// 需下载使用对应的离线合成SDK
//			mTts.setParameter(ResourceUtil.TTS_RES_PATH, getFilesDir().getAbsolutePath());
//		}

		mTts.setParameter(SpeechConstant.VOICE_NAME, "vixr");

		final String strTextToSpeech = "窗前明月光,疑是地上霜";
		mTts.startSpeaking(strTextToSpeech, null);
	}

	public void startui(View view) {

		// 初始化听写Dialog，如果只使用有UI听写功能，无需创建SpeechRecognizer
		// 使用UI听写功能，请根据sdk文件目录下的notice.txt,放置布局文件和图片资源
		RecognizerDialog mIatDialog = new RecognizerDialog(MainActivity.this, null);
		// 设置语法ID和 SUBJECT 为空，以免因之前有语法调用而设置了此参数；或直接清空所有参数，具体可参考 DEMO 的示例。
		mIatDialog.setParameter(SpeechConstant.CLOUD_GRAMMAR, null);
		mIatDialog.setParameter(SpeechConstant.SUBJECT, null);
		// 设置返回结果格式，目前支持json,xml以及plain 三种格式，其中plain为纯听写文本内容
		mIatDialog.setParameter(SpeechConstant.RESULT_TYPE, "json");
		// 此处engineType为“cloud”
		mIatDialog.setParameter(SpeechConstant.ENGINE_TYPE, "cloud");
		// 设置语音输入语言，zh_cn为简体中文
		mIatDialog.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
		// 设置结果返回语言
		mIatDialog.setParameter(SpeechConstant.ACCENT, "mandarin");
		// 设置语音前端点:静音超时时间，单位ms，即用户多长时间不说话则当做超时处理
		// 取值范围{1000～10000}
		mIatDialog.setParameter(SpeechConstant.VAD_BOS, "4000");
		// 设置语音后端点:后端点静音检测时间，单位ms，即用户停止说话多长时间内即认为不再输入，
		// 自动停止录音，范围{0~10000}
		mIatDialog.setParameter(SpeechConstant.VAD_EOS, "1000");
		// 设置标点符号,设置为"0"返回结果无标点,设置为"1"返回结果有标点
		mIatDialog.setParameter(SpeechConstant.ASR_PTT, "1");

		// 开始识别并设置监听器
		mIatDialog.setListener(mRecognizerDialogListener);
		if (mIatDialog != null) {
			// 显示听写对话框
			mIatDialog.show();
		}
	}

	private RecognizerDialogListener mRecognizerDialogListener = new RecognizerDialogListener() {

		@Override
		public void onResult(RecognizerResult result, boolean isLast) {
			System.out.println(result.getResultString());
			System.out.println("isLast:" + isLast);
			Toast.makeText(MainActivity.this, result.getResultString(), 1).show();
		}

		@Override
		public void onError(SpeechError arg0) {

		}
	};

	public void startVoice(View view) {
		// 初始化识别无UI识别对象
		// 使用SpeechRecognizer对象，可根据回调消息自定义界面；
		mIat = SpeechRecognizer.createRecognizer(MainActivity.this, null);

		// 设置语法ID和 SUBJECT 为空，以免因之前有语法调用而设置了此参数；或直接清空所有参数，具体可参考 DEMO 的示例。
		mIat.setParameter(SpeechConstant.CLOUD_GRAMMAR, null);
		mIat.setParameter(SpeechConstant.SUBJECT, null);
		// 设置返回结果格式，目前支持json,xml以及plain 三种格式，其中plain为纯听写文本内容
		mIat.setParameter(SpeechConstant.RESULT_TYPE, "plain");
		// 此处engineType为“cloud”
		mIat.setParameter(SpeechConstant.ENGINE_TYPE, "cloud");
		// 设置语音输入语言，zh_cn为简体中文
		mIat.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
		// 设置结果返回语言
		mIat.setParameter(SpeechConstant.ACCENT, "mandarin");
		// 设置语音前端点:静音超时时间，单位ms，即用户多长时间不说话则当做超时处理
		// 取值范围{1000～10000}
		mIat.setParameter(SpeechConstant.VAD_BOS, "4000");
		// 设置语音后端点:后端点静音检测时间，单位ms，即用户停止说话多长时间内即认为不再输入，
		// 自动停止录音，范围{0~10000}
		mIat.setParameter(SpeechConstant.VAD_EOS, "1000");
		// 设置标点符号,设置为"0"返回结果无标点,设置为"1"返回结果有标点
		mIat.setParameter(SpeechConstant.ASR_PTT, "1");
		// 开始识别，并设置监听器
		mIat.startListening(mRecogListener);
	}

	RecognizerListener mRecogListener = new RecognizerListener() {

		@Override
		public void onVolumeChanged(int arg0, byte[] arg1) {

		}

		@Override
		public void onResult(RecognizerResult result, boolean isLast) {
			System.out.println(result.getResultString());
			System.out.println("isLast:" + isLast);
			Toast.makeText(MainActivity.this, result.getResultString(), 1).show();
		}

		@Override
		public void onEvent(int arg0, int arg1, int arg2, Bundle arg3) {

		}

		@Override
		public void onError(SpeechError arg0) {

		}

		@Override
		public void onEndOfSpeech() {

		}

		@Override
		public void onBeginOfSpeech() {

		}
	};

}
