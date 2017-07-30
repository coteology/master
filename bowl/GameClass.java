public class GameClass{
	FlameClass[] one_game;

	//メイン関数
	public void executeBowling(){
		//フレーム準備
		createFlames__();
		//フレーム初期化
		initCreatedFlames__();
		//ゲームスタート
		startBowlingGame__();
	}

	private void startBowlingGame__(){
		for(int i = 0; i < one_game.length; i++){
			//1フレームスタート
			one_game[i].setStatus(one_game[i].STARTED);
			//ピンをセット
			one_game[i].setPin(10);
			//
			System.out.println("■"+one_game[i].getFlameNumber()+"フレーム目");
			//ボールを投げる
			while(isThrowPattern__(one_game[i]) > 0){
				one_game[i].throwBowl(isThrowPattern__(one_game[i]));
			}
			//計算
			allFlameCalc__(one_game);
			//合計点表示
			//outputResult__(one_game[i].getEndPoint());
			//debug
			outputResult__T(one_game);
		}
	}
	private void createFlames__(){
		// 初期化
		one_game = new FlameClass[10];
		// フレームオブジェクト格納
		for(int i = 0; i < one_game.length; i++){
			one_game[i] = new FlameClass();
		}
	}
	private void initCreatedFlames__(){
		for(int i = 0; i < one_game.length; i++){
			//ステータス、点数を初期化
			one_game[i].init();
			//フレームナンバーを設定
			one_game[i].setFlameNumber(i);
		}
	}
	private int isThrowPattern__(FlameClass in_now_flame){
		//1投目
		if(in_now_flame.getPoint(1) == in_now_flame.DEFAULT){
			//まだ投げていないなら、1投目を投げる
			return 1;
		}
		//2投目
		if((in_now_flame.getPoint(2) == in_now_flame.DEFAULT) && (in_now_flame.getZanPinCount() > 0)){
			//ピンが残っているなら、2投目を投げる
			return 2;
		}
		//2投目
		if((in_now_flame.getPoint(2) == in_now_flame.DEFAULT) && (in_now_flame.getFlameNumber() == 10) && (in_now_flame.getPoint(1) == 10)){
			//10フレーム目で1投目がストライクなら、2投目を投げる
			in_now_flame.setPin(10);
			return 2;
		}
		//3投目
		if( (in_now_flame.getPoint(3) == in_now_flame.DEFAULT) && (in_now_flame.getFlameNumber() == 10) && (in_now_flame.getPoint(1) == 10 || in_now_flame.getZanPinCount() == 0)){
			//10フレーム目で1投目がストライクもしくは、スペアなら、3投目を投げる
			return 3;
		}
		return -1;
	}

	//計算
	private void allFlameCalc__(FlameClass in_all_flame[]){
		for(int i = 0; i < in_all_flame.length; i++){
			//開始されているフレーム かつ 未計算
			if((in_all_flame[i].getStatus() != in_all_flame[i].NOT_STARTED) && (in_all_flame[i].getEndPoint() == in_all_flame[i].DEFAULT)){
				//1フレーム目
				if(in_all_flame[i].getFlameNumber() == 1){
					//ストライクの場合
					if(in_all_flame[i].isStrike()){
						//Next1投目とNext2投目には値が設定済みであること
						if((nextPoint__(in_all_flame[i].getFlameNumber(),in_all_flame,1) != in_all_flame[i].DEFAULT) && (nextPoint__(in_all_flame[i].getFlameNumber(),in_all_flame,2) != in_all_flame[i].DEFAULT)){
							//1投目 + Next1投目+Next2投目
							in_all_flame[i].setEndPoint( in_all_flame[i].getPoint(1) + nextPoint__(in_all_flame[i].getFlameNumber(),in_all_flame,1) + nextPoint__(in_all_flame[i].getFlameNumber(),in_all_flame,2));
						}
					}
					//スペアの場合
					else if(in_all_flame[i].isSpare()){
						//Next1投目には値が設定済みであること
						if(nextPoint__(in_all_flame[i].getFlameNumber(),in_all_flame,1) != in_all_flame[i].DEFAULT){
							//1投目+2投目 + Next1投目
							in_all_flame[i].setEndPoint( in_all_flame[i].getPoint(1) + in_all_flame[i].getPoint(2) + nextPoint__(in_all_flame[i].getFlameNumber(),in_all_flame,1));
						}
					}
					//その他
					else{
						//1投目と2投目の合計点数
						in_all_flame[i].setEndPoint( in_all_flame[i].getPoint(1) + in_all_flame[i].getPoint(2));
					}
				}
				//2投目-9投目
				if(in_all_flame[i].getFlameNumber() > 1 && in_all_flame[i].getFlameNumber() < 10){
					//前フレームの合計点数が設定済み
					if(in_all_flame[i-1].getEndPoint() != in_all_flame[i].DEFAULT){
						//ストライクの場合
						if(in_all_flame[i].isStrike()){
							//Next1投目とNext2投目には値が設定済みであること
							if((nextPoint__(in_all_flame[i].getFlameNumber(),in_all_flame,1) != in_all_flame[i].DEFAULT) && (nextPoint__(in_all_flame[i].getFlameNumber(),in_all_flame,2) != in_all_flame[i].DEFAULT)){
								//前フレームの合計点 + 10 + Next1投目+Next2投目
								in_all_flame[i].setEndPoint(in_all_flame[i-1].getEndPoint() + in_all_flame[i].getPoint(1) + nextPoint__(in_all_flame[i].getFlameNumber(),in_all_flame,1) + nextPoint__(in_all_flame[i].getFlameNumber(),in_all_flame,2));
							}
						}
						//スペアの場合
						else if(in_all_flame[i].isSpare()){
							//Next1投目には値が設定済みであること
							if(nextPoint__(in_all_flame[i].getFlameNumber(),in_all_flame,1) != in_all_flame[i].DEFAULT){
								//前フレームの合計点 +10 + Next1投目
								in_all_flame[i].setEndPoint(in_all_flame[i-1].getEndPoint() + in_all_flame[i].getPoint(1) + in_all_flame[i].getPoint(2) + nextPoint__(in_all_flame[i].getFlameNumber(),in_all_flame,1));
							}
						}
						//その他
						else{
							//前フレームの合計点 + 1投目と2投目の合計点数
							in_all_flame[i].setEndPoint(in_all_flame[i-1].getEndPoint() + in_all_flame[i].getPoint(1) + in_all_flame[i].getPoint(2));
						}
					}
				}
				//10投目
					//前フレームの合計点数が設定済み
						//ストライクの場合
						//	前フレームの合計点 + 10 + Next1投目+Next2投目
						//スペアの場合
						//	前フレームの合計点 + 10 + Next1投目
						//その他
						//	前フレームの合計点 + 1投目と2投目の合計点数
			}
		}
	}
	//次フレーム以降のn投目の点数を取得
	private int nextPoint__(int in_base_flame_number,FlameClass in_all_flame[],int in_wanted_throw_cnt){
		int exsists_cnt =0;
		int next_flame_Number;
		int result = -1;
		//次フレーム
		next_flame_Number = in_base_flame_number;

		for(int i = next_flame_Number; i < in_all_flame.length; i++){
			//n投目の点数を取得するまでループ
			if(result == -1){
				//1投目から3投目
				for(int throw_cnt = 1; throw_cnt < 3; throw_cnt++){
					//n投目に達するまでカウント
					if(in_all_flame[i].getPoint(throw_cnt) != in_all_flame[i].DEFAULT){
						exsists_cnt = exsists_cnt + 1;
					}
					//n投目に達したら、その点数を取得
					if (in_wanted_throw_cnt == exsists_cnt){
						result = in_all_flame[i].getPoint(throw_cnt);
						break;
					}
				}
			}else{
				//n投目の点数を取得したら抜ける
				break;
			}
		}
		return result;
	}
	
	private void outputResult__(int in_end_point){
		if (in_end_point < 0){
			System.out.println("合計点数：点数未確定");
		}else{
			System.out.println("合計点数：" + in_end_point);
		}
		
	}
	private void outputResult__T(FlameClass in_all_flame[]){
		for(int i = 0; i < in_all_flame.length; i++){
			if(in_all_flame[i].getEndPoint() != in_all_flame[i].DEFAULT){
				System.out.println(in_all_flame[i].getFlameNumber() +"フレーム目の合計点数："+in_all_flame[i].getEndPoint());
			}
		}
	}


}