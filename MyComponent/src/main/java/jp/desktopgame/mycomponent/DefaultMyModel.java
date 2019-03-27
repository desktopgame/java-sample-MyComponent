package jp.desktopgame.mycomponent;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import javax.swing.event.ChangeEvent;

/**
 * デフォルトの MyModel の実装クラスです。
 * ユーザは基本的にこれを拡張して新たなモデルを定義できます。
 *
 * 描画をカスタマイズしたいときにモデル側のデータ構造を変更するだけで済むこともあります。
 * 例えば getPathAt をオーバーライドして曲線を描けなくするとか…
 */
public class DefaultMyModel extends AbstractMyModel {
    private List<Point> pathList;

    public DefaultMyModel() {
        this.pathList = new ArrayList<>();
    }

    /**
     * 全てのパスを削除してイベントを発行します。
     */
    public void clearPath() {
        pathList.clear();
        fireStateChanged(new ChangeEvent(this));
    }

    /**
     * パスを追加してイベントを発行します。
     * @param point
     */
    public void addPath(Point point) {
        pathList.add(new Point(point));
        fireStateChanged(new ChangeEvent(this));
    }

    @Override
    public Point getPathAt(int index) {
        return pathList.get(index);
    }

    @Override
    public int getPathCount() {
        return pathList.size();
    }
}
