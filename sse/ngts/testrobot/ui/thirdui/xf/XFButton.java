package sse.ngts.testrobot.ui.thirdui.xf;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

public class XFButton extends JButton implements FocusListener
{

    /**
     * An action used for handling of <Enter>
     * when tabbing over. Assumes the src is a
     * BasicButton and if src has the focus it
     * triggers a doClick().
     * A single instance is used as a flyweight for all BasicButtons.
     */
    class EnterAction extends AbstractAction
    {

        EnterAction()
        {
            super("Enter");
        }

        @Override
        public void actionPerformed(ActionEvent evt)
        {
            XFButton src = (XFButton) evt.getSource();

            // verify we've got the focus
            if (src.hasFocus())
            {
                src.doClick();
            }
        }
    }

    /**
     * Listener for &lt;Enter&gt; key WHEN_FOCUSED.
     * A single instance is used as a flyweight for all BasicButtons.
     **/
    private static EnterAction sEnterAction;
    private int fontSize;
    private int fontStile;
    private int buttonHeight;

    private int buttonWidth;

    /**
     * Constant for a button size.
     */

    public static final int SHORT_BUTTON = 1;

    public static final int LONG_BUTTON = 2;

    public static final int EXTRA_LONG_BUTTON = 3;

    public static final int EXTRA_SHORT_BUTTON = 4;

    public static final int MEDIUM_LONG_BUTTON = 5;

    //
    // ***
    //

    public static final int SUPER_SHORT_BUTTON = 6;

    /**
     * Constructs a new BasicButton
     * @param icon
     *        the icon image to display on the button
     * @param buttonSize
     *        index for the button size
     */
    public XFButton(Icon icon, int buttonSize)
    {
        super(icon);
        setButtonSize(buttonSize);
        init();
    }

    /**
     * Constructs a new BasicButton
     * @param buttonSize
     *        index for the button size
     */
    public XFButton(int buttonSize)
    {
        super();
        setButtonSize(buttonSize);
        init();
    }

    /**
     * Constructs a new BasicButton
     * @param text
     *        the text of the button
     * @param icon
     *        the icon image to display on the button
     * @param buttonSize
     *        index for the button size
     */
    public XFButton(String text, Icon icon, int buttonSize)
    {
        super(text, icon);
        setButtonSize(buttonSize);
        init();
    }

    /**
     * Constructs a new BasicButton
     * @param text
     *        the text of the button
     * @param buttonSize
     *        index for the button size
     */
    public XFButton(String text, int buttonSize)
    {
        super(text);
        setButtonSize(buttonSize);
        init();
    }

    /**
     * Invoked when a component gains the keyboard focus.
     */
    @Override
    public void focusGained(FocusEvent e)
    {
        if (!this.isFocusPainted())
        {
            this.setFocusPainted(true);
        }
    }

    /**
     * Invoked when a component loses the keyboard focus.
     */
    @Override
    public void focusLost(FocusEvent e)
    {
        if (this.isFocusPainted())
        {
            this.setFocusPainted(false);
        }
    }

    private void init()
    {

        // make sure we've got our flyweight in place:
        if (sEnterAction == null)
        {
            sEnterAction = new EnterAction();
        }
        this.setFocusable(true);
        this.addFocusListener(this);

        // trigger this button and not default button when
        // we've got the focus:
        this.registerKeyboardAction(sEnterAction, KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0
                /* modifiers */), JComponent.WHEN_FOCUSED);
    }

    /**
     * The paint method of the JButton is overridden to turn on anti-aliasing.
     */
    @Override
    public void paint(Graphics g)
    {
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        super.paint(g);
    }

    /**
     * Set the button size
     * @param buttonSize
     *        index for the button size
     **/
    final public void setButtonSize(int buttonSize)
    {

        fontSize = this.getFont().getSize();
        fontStile = this.getFont().getStyle();
        buttonHeight = (int) (2.25 * fontSize);

        switch (buttonSize)
        {

            case SHORT_BUTTON:
            {
                buttonWidth = (int) (7.5 * fontSize);
                setPreferredSize(new Dimension(buttonWidth, buttonHeight));
                setMinimumSize(new Dimension(buttonWidth, buttonHeight));
                setMaximumSize(new Dimension(buttonWidth, buttonHeight));
            }
            break;

            case LONG_BUTTON:
            {
                buttonWidth = (int) (11.4 * fontSize);
                setPreferredSize(new Dimension(buttonWidth, buttonHeight));
                setMinimumSize(new Dimension(buttonWidth, buttonHeight));
                setMaximumSize(new Dimension(buttonWidth, buttonHeight));
            }
            break;

            case EXTRA_LONG_BUTTON:
            {
                buttonWidth = (int) (13.0 * fontSize);
                setPreferredSize(new Dimension(buttonWidth, buttonHeight));
                setMinimumSize(new Dimension(buttonWidth, buttonHeight));
                setMaximumSize(new Dimension(buttonWidth, buttonHeight));
            }
            break;
            case EXTRA_SHORT_BUTTON:
            {
                buttonWidth = (int) (5.5 * fontSize);
                setPreferredSize(new Dimension(buttonWidth, buttonHeight));
                setMinimumSize(new Dimension(buttonWidth, buttonHeight));
                setMaximumSize(new Dimension(buttonWidth, buttonHeight));
            }
            break;

            case SUPER_SHORT_BUTTON:
            {
                buttonWidth = (int) (3.5 * fontSize);
                setPreferredSize(new Dimension(buttonWidth, buttonHeight));
                setMinimumSize(new Dimension(buttonWidth, buttonHeight));
                setMaximumSize(new Dimension(buttonWidth, buttonHeight));
            }
            break;

            case MEDIUM_LONG_BUTTON:
            {
                buttonWidth = (int) (9.5 * fontSize);
                setPreferredSize(new Dimension(buttonWidth, buttonHeight));
                setMinimumSize(new Dimension(buttonWidth, buttonHeight));
                setMaximumSize(new Dimension(buttonWidth, buttonHeight));
            }
            break;

            default:
            {
                throw new IllegalArgumentException("Unknown selection '" + buttonSize + "'");
            }

        } // switch

    }
}
