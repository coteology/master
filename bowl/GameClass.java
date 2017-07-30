public class GameClass{
	FlameClass[] one_game;

	//���C���֐�
	public void executeBowling(){
		//�t���[������
		createFlames__();
		//�t���[��������
		initCreatedFlames__();
		//�Q�[���X�^�[�g
		startBowlingGame__();
	}

	private void startBowlingGame__(){
		for(int i = 0; i < one_game.length; i++){
			//1�t���[���X�^�[�g
			one_game[i].setStatus(one_game[i].STARTED);
			//�s�����Z�b�g
			one_game[i].setPin(10);
			//
			System.out.println("��"+one_game[i].getFlameNumber()+"�t���[����");
			//�{�[���𓊂���
			while(isThrowPattern__(one_game[i]) > 0){
				one_game[i].throwBowl(isThrowPattern__(one_game[i]));
			}
			//�v�Z
			allFlameCalc__(one_game);
			//���v�_�\��
			//outputResult__(one_game[i].getEndPoint());
			//debug
			outputResult__T(one_game);
		}
	}
	private void createFlames__(){
		// ������
		one_game = new FlameClass[10];
		// �t���[���I�u�W�F�N�g�i�[
		for(int i = 0; i < one_game.length; i++){
			one_game[i] = new FlameClass();
		}
	}
	private void initCreatedFlames__(){
		for(int i = 0; i < one_game.length; i++){
			//�X�e�[�^�X�A�_����������
			one_game[i].init();
			//�t���[���i���o�[��ݒ�
			one_game[i].setFlameNumber(i);
		}
	}
	private int isThrowPattern__(FlameClass in_now_flame){
		//1����
		if(in_now_flame.getPoint(1) == in_now_flame.DEFAULT){
			//�܂������Ă��Ȃ��Ȃ�A1���ڂ𓊂���
			return 1;
		}
		//2����
		if((in_now_flame.getPoint(2) == in_now_flame.DEFAULT) && (in_now_flame.getZanPinCount() > 0)){
			//�s�����c���Ă���Ȃ�A2���ڂ𓊂���
			return 2;
		}
		//2����
		if((in_now_flame.getPoint(2) == in_now_flame.DEFAULT) && (in_now_flame.getFlameNumber() == 10) && (in_now_flame.getPoint(1) == 10)){
			//10�t���[���ڂ�1���ڂ��X�g���C�N�Ȃ�A2���ڂ𓊂���
			in_now_flame.setPin(10);
			return 2;
		}
		//3����
		if( (in_now_flame.getPoint(3) == in_now_flame.DEFAULT) && (in_now_flame.getFlameNumber() == 10) && (in_now_flame.getPoint(1) == 10 || in_now_flame.getZanPinCount() == 0)){
			//10�t���[���ڂ�1���ڂ��X�g���C�N�������́A�X�y�A�Ȃ�A3���ڂ𓊂���
			return 3;
		}
		return -1;
	}

	//�v�Z
	private void allFlameCalc__(FlameClass in_all_flame[]){
		for(int i = 0; i < in_all_flame.length; i++){
			//�J�n����Ă���t���[�� ���� ���v�Z
			if((in_all_flame[i].getStatus() != in_all_flame[i].NOT_STARTED) && (in_all_flame[i].getEndPoint() == in_all_flame[i].DEFAULT)){
				//1�t���[����
				if(in_all_flame[i].getFlameNumber() == 1){
					//�X�g���C�N�̏ꍇ
					if(in_all_flame[i].isStrike()){
						//Next1���ڂ�Next2���ڂɂ͒l���ݒ�ς݂ł��邱��
						if((nextPoint__(in_all_flame[i].getFlameNumber(),in_all_flame,1) != in_all_flame[i].DEFAULT) && (nextPoint__(in_all_flame[i].getFlameNumber(),in_all_flame,2) != in_all_flame[i].DEFAULT)){
							//1���� + Next1����+Next2����
							in_all_flame[i].setEndPoint( in_all_flame[i].getPoint(1) + nextPoint__(in_all_flame[i].getFlameNumber(),in_all_flame,1) + nextPoint__(in_all_flame[i].getFlameNumber(),in_all_flame,2));
						}
					}
					//�X�y�A�̏ꍇ
					else if(in_all_flame[i].isSpare()){
						//Next1���ڂɂ͒l���ݒ�ς݂ł��邱��
						if(nextPoint__(in_all_flame[i].getFlameNumber(),in_all_flame,1) != in_all_flame[i].DEFAULT){
							//1����+2���� + Next1����
							in_all_flame[i].setEndPoint( in_all_flame[i].getPoint(1) + in_all_flame[i].getPoint(2) + nextPoint__(in_all_flame[i].getFlameNumber(),in_all_flame,1));
						}
					}
					//���̑�
					else{
						//1���ڂ�2���ڂ̍��v�_��
						in_all_flame[i].setEndPoint( in_all_flame[i].getPoint(1) + in_all_flame[i].getPoint(2));
					}
				}
				//2����-9����
				if(in_all_flame[i].getFlameNumber() > 1 && in_all_flame[i].getFlameNumber() < 10){
					//�O�t���[���̍��v�_�����ݒ�ς�
					if(in_all_flame[i-1].getEndPoint() != in_all_flame[i].DEFAULT){
						//�X�g���C�N�̏ꍇ
						if(in_all_flame[i].isStrike()){
							//Next1���ڂ�Next2���ڂɂ͒l���ݒ�ς݂ł��邱��
							if((nextPoint__(in_all_flame[i].getFlameNumber(),in_all_flame,1) != in_all_flame[i].DEFAULT) && (nextPoint__(in_all_flame[i].getFlameNumber(),in_all_flame,2) != in_all_flame[i].DEFAULT)){
								//�O�t���[���̍��v�_ + 10 + Next1����+Next2����
								in_all_flame[i].setEndPoint(in_all_flame[i-1].getEndPoint() + in_all_flame[i].getPoint(1) + nextPoint__(in_all_flame[i].getFlameNumber(),in_all_flame,1) + nextPoint__(in_all_flame[i].getFlameNumber(),in_all_flame,2));
							}
						}
						//�X�y�A�̏ꍇ
						else if(in_all_flame[i].isSpare()){
							//Next1���ڂɂ͒l���ݒ�ς݂ł��邱��
							if(nextPoint__(in_all_flame[i].getFlameNumber(),in_all_flame,1) != in_all_flame[i].DEFAULT){
								//�O�t���[���̍��v�_ +10 + Next1����
								in_all_flame[i].setEndPoint(in_all_flame[i-1].getEndPoint() + in_all_flame[i].getPoint(1) + in_all_flame[i].getPoint(2) + nextPoint__(in_all_flame[i].getFlameNumber(),in_all_flame,1));
							}
						}
						//���̑�
						else{
							//�O�t���[���̍��v�_ + 1���ڂ�2���ڂ̍��v�_��
							in_all_flame[i].setEndPoint(in_all_flame[i-1].getEndPoint() + in_all_flame[i].getPoint(1) + in_all_flame[i].getPoint(2));
						}
					}
				}
				//10����
					//�O�t���[���̍��v�_�����ݒ�ς�
						//�X�g���C�N�̏ꍇ
						//	�O�t���[���̍��v�_ + 10 + Next1����+Next2����
						//�X�y�A�̏ꍇ
						//	�O�t���[���̍��v�_ + 10 + Next1����
						//���̑�
						//	�O�t���[���̍��v�_ + 1���ڂ�2���ڂ̍��v�_��
			}
		}
	}
	//���t���[���ȍ~��n���ڂ̓_�����擾
	private int nextPoint__(int in_base_flame_number,FlameClass in_all_flame[],int in_wanted_throw_cnt){
		int exsists_cnt =0;
		int next_flame_Number;
		int result = -1;
		//���t���[��
		next_flame_Number = in_base_flame_number;

		for(int i = next_flame_Number; i < in_all_flame.length; i++){
			//n���ڂ̓_�����擾����܂Ń��[�v
			if(result == -1){
				//1���ڂ���3����
				for(int throw_cnt = 1; throw_cnt < 3; throw_cnt++){
					//n���ڂɒB����܂ŃJ�E���g
					if(in_all_flame[i].getPoint(throw_cnt) != in_all_flame[i].DEFAULT){
						exsists_cnt = exsists_cnt + 1;
					}
					//n���ڂɒB������A���̓_�����擾
					if (in_wanted_throw_cnt == exsists_cnt){
						result = in_all_flame[i].getPoint(throw_cnt);
						break;
					}
				}
			}else{
				//n���ڂ̓_�����擾�����甲����
				break;
			}
		}
		return result;
	}
	
	private void outputResult__(int in_end_point){
		if (in_end_point < 0){
			System.out.println("���v�_���F�_�����m��");
		}else{
			System.out.println("���v�_���F" + in_end_point);
		}
		
	}
	private void outputResult__T(FlameClass in_all_flame[]){
		for(int i = 0; i < in_all_flame.length; i++){
			if(in_all_flame[i].getEndPoint() != in_all_flame[i].DEFAULT){
				System.out.println(in_all_flame[i].getFlameNumber() +"�t���[���ڂ̍��v�_���F"+in_all_flame[i].getEndPoint());
			}
		}
	}


}