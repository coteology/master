import java.io.*;
import java.util.*;

public class FlameClass{

	// ステータス(定数)
	final int NOT_STARTED = -1;
	final int STARTED = 0;
	final int STRIKE = 1;
	final int  SPARE = 2;
	
	final int FIRST = 1;
	final int SECOND = 2;
	final int THIRD = 3;
	final int DEFAULT = -1;
	// 初期値
	int status = -1;
	int end_point;
	int pin_count;
	// フレーム数
	int flame_num = 0;

	//点数
	int first_point; // 1投目
	int second_point; // 2投目
	int third_point; // 3投目

	public void init(){
		setStatus(NOT_STARTED);
		setPoint(FIRST,DEFAULT);
		setPoint(SECOND,DEFAULT);
		setPoint(THIRD,DEFAULT);
		setEndPoint(DEFAULT);
	}
	// setPoint()
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
	// setEndPoint()
	public void setEndPoint(int in_point){
		end_point = in_point;
	}
	
	// setFlameNumber()
	public void setFlameNumber(int current_flame){
		++current_flame;
		flame_num = current_flame;
	}
	// setStatus(ステータス)
	public void setStatus(int in_status){
		status = in_status;
	}
	// getStatus()
	public int getStatus(){
		return status;
	}
	// getCurrentFlame()
	public int getFlameNumber(){
		return flame_num;
	}

	// getPoint(投ごとの点数)
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
	// getEndPoint()
	public int getEndPoint(){
		return end_point;
	}
	
	public boolean isStrike(){
		if (getPoint(FIRST) == 10 ){
			return true;
		}
		return false;
	}
	public boolean isSpare(){
		if(getPoint(FIRST) != 10 && (getPoint(FIRST) + getPoint(SECOND) == 10)){
			return true;
		}
		return false;
	}

	//throwBowl
	public void throwBowl(int in_throw_cnt){
		try{
			//n投目：倒したピン数を入力⇒
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
	//setPin
	public void setPin(int in_set_pin){
		pin_count = in_set_pin;
	}

	//getPinCount残ピン
	public int getZanPinCount(){
		return pin_count;
	}
	
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
