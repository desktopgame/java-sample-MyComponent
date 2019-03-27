package jp.desktopgame.mycomponent;

import javax.swing.JComponent;

/**
 * マウスドラッグで線を描画できるコンポーネント。
 */
public class MyComponent extends JComponent {
    private MyModel model;

    public MyComponent() {
        setModel(new DefaultMyModel());
        //おまじない
        //どんなクラスも必ずコンストラクタの最後でこれを呼び出す
        //ただし親クラスが呼び出している場合は例外。
        updateUI();
    }

    @Override
    public void updateUI() {
        // 本当はこっち
        // ルックアンドフィールによって紐づけられたクラスを探索する(おそらく)
        // setUI((MyComponent) UIManager.getUI(this));
        setUI(new MyComponentUI());
    }

    public void setUI(MyComponentUI ui) {
        super.setUI(ui);
    }

    public MyComponentUI getUI() {
        return (MyComponentUI) ui;
    }

    public String getUIClassID() {
        return "MyComponentUI";
    }

    public void setModel(MyModel newModel) {
        //データ構造が上書きされるときは必ずイベントを発行すること。
        //ビューはモデルを監視して再描画を行いますが、
        //モデルの参照そのものが変更されてしまうと、
        //古いモデルの監視を終了して、新たなモデルの監視を開始しなければならないためです。
        MyModel old = model;
        this.model = newModel;
        firePropertyChange("model", old, newModel);
    }

    public MyModel getModel() {
        return model;
    }
}
