import java.io.*;
import java.util.*;

public class FlameClass{

	// 1�t���[�����Ƃ̃X�e�[�^�X
	private int status;                    //�X�e�[�^�X
	final int NOT_STARTED = -1;            //���J�n
	final int STARTED = 0;                 //�J�n�ς�
	//����
	final int FIRST = 1;                   //1����
	final int SECOND = 2;                  //2����
	final int THIRD = 3;                   //3����
	final int DEFAULT = -1;                //n���ڂ̏����l

	private int end_point;                 //1�t���[�����Ƃ̍��v�_
	private int pin_count;                 //1�t���[�����Ƃ̃s����

	private int flame_num = 0;             //1�Q�[�����̃t���[���ʒu

	//�_��
	private int first_point;               // 1���ڂ̓_��
	private int second_point;              // 2���ڂ̓_��
	private int third_point;               // 3���ڂ̓_��

	public void init(){
		setStatus(NOT_STARTED);
		setPoint(FIRST,DEFAULT);
		setPoint(SECOND,DEFAULT);
		setPoint(THIRD,DEFAULT);
		setEndPoint(DEFAULT);
	}

	/**
	* ���_���Z�b�g
	* @param in_throw_cnt ����
	* @param in_point ���_
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
	* ���v�_���Z�b�g
	* @param in_point ���v�_
	* @param 
	*/
	public void setEndPoint(int in_point){
		end_point = in_point;
	}
	
	/**
	* �t���[���i���o�[���Z�b�g
	* @param current_flame �����_�̃t���[���i���o�[
	* @param 
	*/
	public void setFlameNumber(int current_flame){
		++current_flame;
		flame_num = current_flame;
	}
	/**
	* �X�e�[�^�X���Z�b�g
	* @param in_status �X�e�[�^�X
	* @param 
	*/
	public void setStatus(int in_status){
		status = in_status;
	}
	/**
	* �X�e�[�^�X���Q�b�g
	* @param 
	* @param 
	*/
	// getStatus()
	public int getStatus(){
		return status;
	}
	/**
	* �t���[���i���o�[���Q�b�g
	* @param 
	* @param 
	*/
	public int getFlameNumber(){
		return flame_num;
	}
	/**
	* n���ڂ̃|�C���g���Q�b�g
	* @param in_throw_cnt n����
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
	* ���v�_���Q�b�g
	* @param in_throw_cnt n����
	* @param 
	*/
	public int getEndPoint(){
		return end_point;
	}
	/**
	* �X�g���C�N��
	* @param 
	* @param 
	*/
	public boolean isStrike(){
		//1���ڂ�10�_
		if (getPoint(FIRST) == 10 ){
			return true;
		}
		return false;
	}
	/**
	* �X�y�A��
	* @param 
	* @param 
	*/
	public boolean isSpare(){
		//1���ڂ�10�_�ȊO��1����+2���ڂ�10�_
		if(getPoint(FIRST) != 10 && (getPoint(FIRST) + getPoint(SECOND) == 10)){
			return true;
		}
		return false;
	}
	/**
	* n���ڂɃ{�[���𓊂���
	* @param in_throw_cnt n����
	* @param 
	*/
	public void throwBowl(int in_throw_cnt){
		try{
			//n����
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
	/**
	* �s�����Z�b�g
	* @param in_set_pin �s����
	* @param 
	*/
	public void setPin(int in_set_pin){
		pin_count = in_set_pin;
	}
	/**
	* �s�����Q�b�g
	* @param 
	* @param 
	*/
	public int getZanPinCount(){
		return pin_count;
	}
	/**
	* ���͒l�`�F�b�N
	* @param in_downpin �s���̓|��
	* @param in_zanpin �c�s����
	*/
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
