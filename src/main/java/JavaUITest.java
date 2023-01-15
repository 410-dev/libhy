import me.hy.libhyextended.javaui.UIWindow;
import me.hy.libhyextended.javaui.UIWindowManager;
import me.hy.libhyextended.javaui.components.*;
import me.hy.libhyextended.javaui.components.organizers.*;
import me.hy.libhyextended.javaui.properties.UIWindowProperty;

import javax.swing.event.MouseInputAdapter;
import java.awt.event.MouseEvent;

public class JavaUITest {
    public static void main(String[] args) {
        UIWindowManager.newWindow(
            new UIWindowProperty()
                .name("test")
                .title("Test")
        ).loadScene(
            new UIScene()
                .components(
                    new VerticalStack()
                        .components(
                            new UILabel("Hello World")
                                .width(100),
                            new UIButton("Click Me")
                                .width(100)
                                .action(new MouseInputAdapter() {
                                    public void mouseClicked(MouseEvent e) {
                                        UIWindowManager.newWindow(
                                            new UIWindowProperty()
                                                .name("Popup")
                                                .title("Popup")
                                        ).loadScene(
                                            new UIScene()
                                                .components(
                                                    new UILabel("Hello World")
                                                        .width(100)
                                                        .height(100)
                                                )
                                        );
                                    }
                                }
                            )
                        )
                )
        );
    }
}

