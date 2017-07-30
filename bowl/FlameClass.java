import java.io.*;
import java.util.*;

public class FlameClass{

	// �X�e�[�^�X(�萔)
	final int NOT_STARTED = -1;
	final int STARTED = 0;
	final int STRIKE = 1;
	final int  SPARE = 2;
	
	final int FIRST = 1;
	final int SECOND = 2;
	final int THIRD = 3;
	final int DEFAULT = -1;
	// �����l
	int status = -1;
	int end_point;
	int pin_count;
	// �t���[����
	int flame_num = 0;

	//�_��
	int first_point; // 1����
	int second_point; // 2����
	int third_point; // 3����

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
	// setStatus(�X�e�[�^�X)
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

	// getPoint(�����Ƃ̓_��)
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
			//n���ځF�|�����s��������́�
			System.out.println(in_throw_cnt + "���ځF" + "�|�����s��������́�");
			int down_pin_cnt = new java.util.Scanner(System.in).nextInt();

			//�s�����`�F�b�N
			preSetPointCheck__(down_pin_cnt,pin_count);

			// �c�s��
			setPin(getZanPinCount() - down_pin_cnt);
			// n���ڂœ|�����s��
			setPoint(in_throw_cnt,down_pin_cnt);
			
		}catch(InputMismatchException  e){
			System.err.println("�G���[" + e.getMessage());
			throwBowl(in_throw_cnt);
		}
		catch(IllegalArgumentException  e){
			System.err.println("�G���[" + e.getMessage());
			throwBowl(in_throw_cnt);
		}

	}
	//setPin
	public void setPin(int in_set_pin){
		pin_count = in_set_pin;
	}

	//getPinCount�c�s��
	public int getZanPinCount(){
		return pin_count;
	}
	
	private void preSetPointCheck__(int in_downpin,int in_zanpin){
		//���͒l��0-10�ȉ�����Ȃ�
		if(in_downpin < 0 || in_downpin > 10){
			throw new IllegalArgumentException
            ("0�`10�ȉ��̒l����͂��Ă�������");
		}
		//�|�����s����������
		if((in_zanpin - in_downpin) < 0){
			throw new IllegalArgumentException
            ("�|�����s�����������܂�");
		}
	}
}
