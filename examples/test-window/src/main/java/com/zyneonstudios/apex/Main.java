package com.zyneonstudios.apex;

import com.formdev.flatlaf.FlatDarkLaf;
import io.avaje.webview.Webview;

import javax.swing.*;
import java.awt.*;

public class Main {

  private static Webview webview;
  private static final JLabel maximizeState = new JLabel("Hidden: false");
  private static final JLabel minimizeState = new JLabel("Hidden: false");
  private static final JLabel hiddenState = new JLabel("Hidden: false");

  static void main(String[] args) {
    FlatDarkLaf.setup();

    JFrame frame = new JFrame();
    frame.setSize(600, 400);
    frame.setResizable(true);
    frame.setAlwaysOnTop(true);
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

    JPanel controlPanel = new JPanel(new BorderLayout());
    frame.add(controlPanel);

    JTextField urlInput = new JTextField(30);
    urlInput.setText("https://google.com");
    urlInput.addActionListener(_ -> {
      String url = urlInput.getText();
      webview.navigate(url);
    });

    controlPanel.add(urlInput, BorderLayout.CENTER);

    JPanel maximizePanel = new JPanel();
    maximizePanel.setLayout(new FlowLayout());
    maximizeState.setForeground(Color.WHITE);
    maximizePanel.add(maximizeState);

    JButton maximize = new JButton("Maximize");
    maximize.addActionListener(e -> {
      try {
        maximize();
      } catch (InterruptedException ex) {
        throw new RuntimeException(ex);
      }
    });
    maximizePanel.add(maximize);

    JButton unmaximize = new JButton("Unmaximize");
    unmaximize.addActionListener(e -> {
      try {
        unmaximize();
      } catch (InterruptedException ex) {
        throw new RuntimeException(ex);
      }
    });
    maximizePanel.add(unmaximize);

    JButton toggleMaximize = new JButton("toggle Maxmimazitaion");
    toggleMaximize.addActionListener(e -> toggleMaximize());
    maximizePanel.add(toggleMaximize);

    JPanel minimizePanel = new JPanel();
    minimizePanel.setLayout(new FlowLayout());
    minimizePanel.add(minimizeState);
    minimizeState.setForeground(Color.WHITE);

    JButton minimize = new JButton("Minimize");
    minimize.addActionListener(e -> {
      try {
        minimize();
      } catch (InterruptedException ex) {
        throw new RuntimeException(ex);
      }
    });
    minimizePanel.add(minimize);

    JButton unminimize = new JButton("Unminimize");
    unminimize.addActionListener(e -> {
      try {
        unminimize();
      } catch (InterruptedException ex) {
        throw new RuntimeException(ex);
      }
    });
    minimizePanel.add(unminimize);

    JButton toggleMinimize = new JButton("toggle Minimization");
    toggleMinimize.addActionListener(e -> toggleMinimize());
    minimizePanel.add(toggleMinimize);

    JPanel hidePanel = new JPanel();
    hidePanel.setLayout(new FlowLayout());
    hidePanel.add(hiddenState);
    hiddenState.setForeground(Color.WHITE);

    JButton show = new JButton("Show");
    show.addActionListener(e -> {
      try {
        show();
      } catch (InterruptedException ex) {
        throw new RuntimeException(ex);
      }
    });
    hidePanel.add(show);

    JButton hide = new JButton("Hide");
    hide.addActionListener(e -> {
      try {
        hide();
      } catch (InterruptedException ex) {
        throw new RuntimeException(ex);
      }
    });
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

  private static void maximize() throws InterruptedException {
    webview.maximizeWindow();
    Thread.sleep(1);
    maximizeState.setText("Maximized: " + webview.isMaximized());
  }

  private static void unmaximize() throws InterruptedException {
    webview.unmaximizeWindow();
    Thread.sleep(1);
    maximizeState.setText("Maximized: " + webview.isMaximized());
  }

  private static void toggleMaximize() {
    try {
      if (webview.isMaximized()) {
        unmaximize();
      } else {
        maximize();
      }
    } catch (Exception _) {
    }
  }

  private static void minimize() throws InterruptedException {
    webview.minimizeWindow();
    Thread.sleep(1);
    minimizeState.setText("Minimized: " + webview.isMinimized());
  }

  private static void unminimize() throws InterruptedException {
    webview.unminimizeWindow();
    Thread.sleep(1);
    minimizeState.setText("Minimized: " + webview.isMinimized());
  }

  private static void toggleMinimize() {
    try {
      if (webview.isMinimized()) {
        unminimize();
      } else {
        minimize();
      }
    } catch (Exception _) {
    }
  }

  private static void hide() throws InterruptedException {
    webview.hideWindow();
    Thread.sleep(1);
    hiddenState.setText("Hidden: " + webview.isHidden());
  }

  private static void show() throws InterruptedException {
    webview.showWindow();
    Thread.sleep(1);
    hiddenState.setText("Hidden: " + webview.isHidden());
  }

  private static void toggleHidden() {
    try {
      if (webview.isHidden()) {
        show();
      } else {
        hide();
      }
    } catch (Exception _) {
    }
  }
}
