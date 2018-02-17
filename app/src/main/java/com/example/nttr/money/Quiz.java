package com.example.nttr.money;

/**
 * Created by nttr on 2017/12/13.
 */

public class Quiz {
    /* 問題 */
    String content;
    //選択肢
    String option1;
    String option2;
    String option3;
    //答えの文字
    String answer;

    //コンストラクタ（Quizクラスに変数とクラスのインスタンスを生成するため、new クラス名（引数）とする際のメソッド）
    //ctrl + enterキーで作成
    public Quiz(String content, String option1, String option2, String option3, String answer) {
        //thisはクラスQuiz
        this.content = content;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.answer = answer;
    }

    //Quiz quiz = new Quiz("問題文", "選択肢1", "選択肢2", "選択肢3", "答え");
}
