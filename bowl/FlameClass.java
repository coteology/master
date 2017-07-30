import java.io.*;
import java.util.*;

public class FlameClass{

	// 1フレームごとのステータス
	private int status;                    //ステータス
	final int NOT_STARTED = -1;            //未開始
	final int STARTED = 0;                 //開始済み
	//投数
	final int FIRST = 1;                   //1投目
	final int SECOND = 2;                  //2投目
	final int THIRD = 3;                   //3投目
	final int DEFAULT = -1;                //n投目の初期値

	private int end_point;                 //1フレームごとの合計点
	private int pin_count;                 //1フレームごとのピン数

	private int flame_num = 0;             //1ゲーム中のフレーム位置

	//点数
	private int first_point;               // 1投目の点数
	private int second_point;              // 2投目の点数
	private int third_point;               // 3投目の点数

	public void init(){
		setStatus(NOT_STARTED);
		setPoint(FIRST,DEFAULT);
		setPoint(SECOND,DEFAULT);
		setPoint(THIRD,DEFAULT);
		setEndPoint(DEFAULT);
	}

	/**
	* 得点をセット
	* @param in_throw_cnt 投数
	* @param in_point 得点
	*/
	public void setPoint(int in_throw_cnt,int in_point){
		if(in_throw_cnt == FIRST){
			first_point = in_point;
		}
		if(in_throw_cnt == SECOND){
			second_point = in_point;
		}
		if(in_throw_cnt == THIRD){
			third_point = in_point;
		}
		//point.put(in_throw_cnt,in_point);
	}
	/**
	* 合計点をセット
	* @param in_point 合計点
	* @param 
	*/
	public void setEndPoint(int in_point){
		end_point = in_point;
	}
	
	/**
	* フレームナンバーをセット
	* @param current_flame 現時点のフレームナンバー
	* @param 
	*/
	public void setFlameNumber(int current_flame){
		++current_flame;
		flame_num = current_flame;
	}
	/**
	* ステータスをセット
	* @param in_status ステータス
	* @param 
	*/
	public void setStatus(int in_status){
		status = in_status;
	}
	/**
	* ステータスをゲット
	* @param 
	* @param 
	*/
	// getStatus()
	public int getStatus(){
		return status;
	}
	/**
	* フレームナンバーをゲット
	* @param 
	* @param 
	*/
	public int getFlameNumber(){
		return flame_num;
	}
	/**
	* n投目のポイントをゲット
	* @param in_throw_cnt n投目
	* @param 
	*/
	public int getPoint(int in_throw_cnt){
		if(in_throw_cnt == FIRST){
			return first_point;
		}
		if(in_throw_cnt == SECOND){
			return second_point;
		}
		if(in_throw_cnt == THIRD){
			return third_point;
		}
		return 0;
	}
	/**
	* 合計点をゲット
	* @param in_throw_cnt n投目
	* @param 
	*/
	public int getEndPoint(){
		return end_point;
	}
	/**
	* ストライクか
	* @param 
	* @param 
	*/
	public boolean isStrike(){
		//1投目が10点
		if (getPoint(FIRST) == 10 ){
			return true;
		}
		return false;
	}
	/**
	* スペアか
	* @param 
	* @param 
	*/
	public boolean isSpare(){
		//1投目が10点以外で1投目+2投目が10点
		if(getPoint(FIRST) != 10 && (getPoint(FIRST) + getPoint(SECOND) == 10)){
			return true;
		}
		return false;
	}
	/**
	* n投目にボールを投げる
	* @param in_throw_cnt n投目
	* @param 
	*/
	public void throwBowl(int in_throw_cnt){
		try{
			//n投目
			System.out.println(in_throw_cnt + "投目：" + "倒したピン数を入力⇒");
			int down_pin_cnt = new java.util.Scanner(System.in).nextInt();
			//ピン数チェック
			preSetPointCheck__(down_pin_cnt,pin_count);
			// 残ピン
			setPin(getZanPinCount() - down_pin_cnt);
			// n投目で倒したピン
			setPoint(in_throw_cnt,down_pin_cnt);
		}catch(InputMismatchException  e){
			System.err.println("エラー" + e.getMessage());
			throwBowl(in_throw_cnt);
		}
		catch(IllegalArgumentException  e){
			System.err.println("エラー" + e.getMessage());
			throwBowl(in_throw_cnt);
		}

	}
	/**
	* ピンをセット
	* @param in_set_pin ピン数
	* @param 
	*/
	public void setPin(int in_set_pin){
		pin_count = in_set_pin;
	}
	/**
	* ピンをゲット
	* @param 
	* @param 
	*/
	public int getZanPinCount(){
		return pin_count;
	}
	/**
	* 入力値チェック
	* @param in_downpin ピンの倒数
	* @param in_zanpin 残ピン数
	*/
	private void preSetPointCheck__(int in_downpin,int in_zanpin){
		//入力値が0-10以下じゃない
		if(in_downpin < 0 || in_downpin > 10){
			throw new IllegalArgumentException
            ("0～10以下の値を入力してください");
		}
		//倒したピン数多すぎ
		if((in_zanpin - in_downpin) < 0){
			throw new IllegalArgumentException
            ("倒したピン数多すぎます");
		}
	}
}
