package io.avaje.webview;

import javax.swing.*;
import java.awt.*;

public class Main {

  private static Webview webview;
  private static JLabel maximizeState = new JLabel("false");
  private static JLabel minimizeState = new JLabel("false");
  private static JLabel hiddenState = new JLabel("false");

  static void main(String[] args) {
    JFrame frame = new JFrame();
    frame.setSize(420, 200);
    frame.setMinimumSize(new Dimension(420, 200));
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setLayout(new FlowLayout());

    Thread.ofPlatform().start(() -> {
      try {
        webview = Webview.builder()
          .title("My Webview")
          .width(800)
          .height(600)
          .minSize(400, 300)
          .enableDeveloperTools(true)
          .navigate("https://google.com")
          .build();

        webview.bind("startDrag", (_) -> {
          startDrag();
          return null;
        });

        webview.bind("close", (_) -> {
          close();
          return null;
        });

        webview.bind("maximize", (_) -> {
          maximize();
          return null;
        });

        webview.bind("unmaximize", (_) -> {
          unmaximize();
          return null;
        });

        webview.bind("toggleMaximize", (_) -> {
          toggleMaximize();
          return null;
        });

        webview.bind("minimize", (_) -> {
          minimize();
          return null;
        });

        webview.bind("unminimize", (_) -> {
          unminimize();
          return null;
        });

        webview.bind("toggleMinimize", (_) -> {
          toggleMinimize();
          return null;
        });

        webview.run();
        System.exit(0);
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    });

    JPanel maximizePanel = new JPanel();
    maximizePanel.setLayout(new FlowLayout());
    maximizePanel.add(maximizeState);

    JButton maximize = new JButton("Maximize");
    maximize.addActionListener(e -> maximize());
    maximizePanel.add(maximize);

    JButton unmaximize = new JButton("Unmaximize");
    unmaximize.addActionListener(e -> unmaximize());
    maximizePanel.add(unmaximize);

    JButton toggleMaximize = new JButton("toggle Maxmimazitaion");
    toggleMaximize.addActionListener(e -> toggleMaximize());
    maximizePanel.add(toggleMaximize);

    JPanel minimizePanel = new JPanel();
    minimizePanel.setLayout(new FlowLayout());
    minimizePanel.add(minimizeState);

    JButton minimize = new JButton("Minimize");
    minimize.addActionListener(e -> minimize());
    minimizePanel.add(minimize);

    JButton unminimize = new JButton("Unminimize");
    unminimize.addActionListener(e -> unminimize());
    minimizePanel.add(unminimize);

    JButton toggleMinimize = new JButton("toggle Minimization");
    toggleMinimize.addActionListener(e -> toggleMinimize());
    minimizePanel.add(toggleMinimize);

    JPanel hidePanel = new JPanel();
    hidePanel.setLayout(new FlowLayout());
    hidePanel.add(hiddenState);

    JButton show = new JButton("Show");
    show.addActionListener(e -> show());
    hidePanel.add(show);

    JButton hide = new JButton("Hide");
    hide.addActionListener(e -> hide());
    hidePanel.add(hide);

    JButton toggleHidden = new JButton("toggle ShowState");
    toggleHidden.addActionListener(e -> toggleHidden());
    hidePanel.add(toggleHidden);

    frame.add(maximizePanel);
    frame.add(minimizePanel);
    frame.add(hidePanel);
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
  }

  private static void startDrag() {
    webview.startWindowDrag();
  }

  private static void close() {
    webview.close();
  }

  private static void maximize() {
    webview.maximizeWindow();
    maximizeState.setText(webview.isMaximized()+"");
  }

  private static void unmaximize() {
    webview.unmaximizeWindow();
    maximizeState.setText(webview.isMaximized()+"");
  }

  private static void toggleMaximize() {
    if (webview.isMaximized()) {
      webview.unmaximizeWindow();
    } else {
      webview.maximizeWindow();
    }
  }

  private static void minimize() {
    webview.minimizeWindow();
    minimizeState.setText(webview.isMinimized()+"");
  }

  private static void unminimize() {
    webview.unminimizeWindow();
    minimizeState.setText(webview.isMinimized()+"");
  }

  private static void toggleMinimize() {
    if (webview.isMinimized()) {
      webview.unminimizeWindow();
    } else {
      webview.minimizeWindow();
    }
  }

  private static void hide() {
    webview.hideWindow();
    hiddenState.setText(webview.isHidden()+"");
  }

  private static void show() {
    webview.showWindow();
    hiddenState.setText(webview.isHidden()+"");
  }

  private static void toggleHidden() {
    if (webview.isHidden()) {
      webview.showWindow();
    } else {
      webview.hideWindow();
    }
  }
}
