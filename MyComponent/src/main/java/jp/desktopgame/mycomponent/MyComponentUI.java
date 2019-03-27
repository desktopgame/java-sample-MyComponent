package jp.desktopgame.mycomponent;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.JComponent;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.ComponentUI;

/**
 * MyComponentに対応するUIクラス。
 */
public class MyComponentUI extends ComponentUI {
    private MouseAdapter mouseHandler;
    private PropertyChangeListener propertyChangeHandler;
    private ChangeListener modelHandler;
    private Optional<MyComponent> selfOpt;
    private DefaultMyModel model;

    public MyComponentUI() {
        this.selfOpt = Optional.empty();
    }

    @Override
    public void installUI(JComponent c) {
        System.out.println("install");
        this.selfOpt = Optional.of((MyComponent) c);
        this.model = (DefaultMyModel) selfOpt.get().getModel();
        //モデルの変更を監視して再描画を行う。
        this.modelHandler = new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                c.repaint();
            }
        };
        //マウスの軌跡を監視してモデルを変更する。
        this.mouseHandler = new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                model.addPath(e.getPoint());
            }
        };
        //モデル自体の変更を監視してモデルを監視するようにする
        this.propertyChangeHandler = new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals("model")) {
                    Object oldValue = evt.getOldValue();
                    Object newValue = evt.getNewValue();
                    if (oldValue != null) {
                        ((MyModel) oldValue).removeChangeListener(modelHandler);
                    }
                    model = (DefaultMyModel) newValue;
                    model.addChangeListener(modelHandler);
                }
            }
        };
        //ここでコンポーネントにイベントリスナーを登録します。
        model.addChangeListener(modelHandler);
        c.addPropertyChangeListener(propertyChangeHandler);
        c.addMouseListener(mouseHandler);
        c.addMouseMotionListener(mouseHandler);
        c.setFocusable(true);
        c.grabFocus();
    }

    @Override
    public void uninstallUI(JComponent c) {
        System.out.println("uninstall");
        //ここでコンポーネントからイベントリスナーを削除します。
        model.removeChangeListener(modelHandler);
        c.removePropertyChangeListener(propertyChangeHandler);
        c.removeMouseListener(mouseHandler);
        c.removeMouseMotionListener(mouseHandler);
        //また、可能なら installUI で生成したオブジェクトへの参照を断つ
        //こうすることで GC に回収されることを促す。
        this.model = null;
        this.mouseHandler = null;
        this.selfOpt = Optional.empty();
    }

    @Override
    public void paint(Graphics g, JComponent c) {
        if (model.getPathCount() < 2) {
            return;
        }
        Point tail = null;
        Color defColor = g.getColor();
        g.setColor(Color.RED);
        for (int i = 0; i < model.getPathCount(); i++) {
            Point next = model.getPathAt(i);
            if (tail == null) {
                tail = model.getPathAt(++i);
                g.drawLine(next.x, next.y, tail.x, tail.y);
            } else {
                g.drawLine(tail.x, tail.y, next.x, next.y);
                tail = next;
            }
        }
        g.setColor(defColor);
    }
}
