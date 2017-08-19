public class GameClass{
	FlameClass[] one_game;

	/**
	* ボーリングを実行する
	* @param 
	* @param 
	*/
	public void executeBowling(){
		//フレーム準備
		createFlames__();
		//フレーム初期化
		initCreatedFlames__();
		//ゲームスタート
		startBowlingGame__();
	}
	/**
	* ボーリングを開始
	* @param 
	* @param 
	*/
	private void startBowlingGame__(){
		for(int i = 0; i < one_game.length; i++){
			//1フレームスタート
			one_game[i].setStatus(one_game[i].STARTED);
			//ピンをセット
			one_game[i].setPin(10);
			//nフレーム目か表示
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
	/**
	* フレームを作成
	* @param 
	* @param 
	*/
	private void createFlames__(){
		// 初期化
		one_game = new FlameClass[10];
		// フレームオブジェクト格納
		for(int i = 0; i < one_game.length; i++){
			one_game[i] = new FlameClass();
		}
	}
	/**
	* フレームを初期化
	* @param 
	* @param 
	*/
	private void initCreatedFlames__(){
		for(int i = 0; i < one_game.length; i++){
			//ステータス、点数を初期化
			one_game[i].init();
			//フレームナンバーを設定
			one_game[i].setFlameNumber(i);
		}
	}
	/**
	* n投目のボールを投げていいか判定
	* @param in_now_flame 現フレーム
	* @param 
	*/
	private int isThrowPattern__(FlameClass in_now_flame){
		int result = -1;
		//1投目
		if(in_now_flame.getPoint(1) == in_now_flame.DEFAULT){
			//まだ投げていないなら、1投目を投げる
			result = in_now_flame.FIRST;
		}//2投目
		else if((in_now_flame.getPoint(2) == in_now_flame.DEFAULT) && (in_now_flame.getZanPinCount() > 0)){
			//ピンが残っているなら、2投目を投げる
			result = in_now_flame.SECOND;
		}//2投目
		else if((in_now_flame.getPoint(2) == in_now_flame.DEFAULT) && (in_now_flame.getFlameNumber() == 10) && (in_now_flame.isStrike())){
			//10フレーム目で1投目がストライクなら、
			//ピンをセット
			//2投目を投げる
			in_now_flame.setPin(10);
			result = in_now_flame.SECOND;
		}//3投目
		else if( (in_now_flame.getPoint(3) == in_now_flame.DEFAULT) && (in_now_flame.getFlameNumber() == 10) && (in_now_flame.isStrike() || in_now_flame.isSpare())){
			//10フレーム目で1投目がストライクもしくは、スペアなら、3投目を投げる
			in_now_flame.setPin(10);
			result = in_now_flame.THIRD;
		}
		return result;
	}
	/**
	* 計算処理
	* @param in_all_flame[] 全フレーム 
	* @param 
	*/
	private void allFlameCalc__(FlameClass in_all_flame[]){
		for(int i = 0; i < in_all_flame.length; i++){
			//ステータスが開始されていて、合計点が未計算の場合
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
				if(in_all_flame[i].getFlameNumber() > 1 && in_all_flame[i].getFlameNumber() <= 9){
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
				//10フレーム目
				if(in_all_flame[i].getFlameNumber() == 10){
					//前フレームの合計点数が設定済み
					if(in_all_flame[i-1].getEndPoint() != in_all_flame[i].DEFAULT){
						//ストライクの場合
						if(in_all_flame[i].isStrike()){
							if((in_all_flame[i].getPoint(1) != in_all_flame[i].DEFAULT) && (in_all_flame[i].getPoint(2) != in_all_flame[i].DEFAULT) && (in_all_flame[i].getPoint(3) != in_all_flame[i].DEFAULT)){
								//前フレームの合計点 + 1投目 + 2投目 + 3投目
								in_all_flame[i].setEndPoint(in_all_flame[i-1].getEndPoint() + in_all_flame[i].getPoint(1) + in_all_flame[i].getPoint(2) + in_all_flame[i].getPoint(3));
							}
						}
						//スペアの場合
						else if(in_all_flame[i].isSpare()){
							if((in_all_flame[i].getPoint(1) != in_all_flame[i].DEFAULT) && (in_all_flame[i].getPoint(2) != in_all_flame[i].DEFAULT) && (in_all_flame[i].getPoint(3) != in_all_flame[i].DEFAULT)){
								//前フレームの合計点 + 1投目 + 2投目 + 3投目
								in_all_flame[i].setEndPoint(in_all_flame[i-1].getEndPoint() + in_all_flame[i].getPoint(1) + in_all_flame[i].getPoint(2) + in_all_flame[i].getPoint(3));
							}
						}
						//その他
						else{
							//前フレームの合計点 + 1投目と2投目の合計点数
							in_all_flame[i].setEndPoint(in_all_flame[i-1].getEndPoint() + in_all_flame[i].getPoint(1) + in_all_flame[i].getPoint(2));
						}
					}
				}
			}
		}
	}
	/**
	* 次フレーム以降のn投目の点数を取得
	* @param in_base_flame_number 現在のフレーム
	* @param in_all_flame[] 参照するフレーム
	* @param in_wanted_throw_cnt 点数を取得したいn投目
	*/
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
	/**
	* 合計点表示
	* @param in_all_flame[] 参照するフレーム
	* @param 
	*/
	private void outputResult__T(FlameClass in_all_flame[]){
		int max_point = 0;
		for(int i = 0; i < in_all_flame.length; i++){
			if(in_all_flame[i].getEndPoint() != in_all_flame[i].DEFAULT){
				//合計点の最大を取得
				max_point = Math.max(max_point,in_all_flame[i].getEndPoint());
			}
		}
		System.out.println("合計点数："+max_point);
	}


}