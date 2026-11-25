package UI.Components;

import UI.Theme.AppIcon;
import UI.Theme.IconColor;
import UI.Theme.UITheme;

import javax.swing.*;

public class SidebarButton extends AppButton {
    private ImageIcon activeIcon;
    private ImageIcon inactiveIcon;
    private boolean active;

    public SidebarButton(String text) {
        super(text, UITheme.BG_DARK, UITheme.TEXT_LIGHT, IconColor.LIGHT);
        alignLeft();
        setActive(false);
    }

    public SidebarButton icon(AppIcon icon) {
        return icon(icon, IconColor.ACCENT, IconColor.LIGHT);
    }

    public SidebarButton icon(AppIcon icon, IconColor activeColor, IconColor inactiveColor) {
        this.activeIcon = (icon != null && activeColor != null)
                ? icon.get(activeColor, UITheme.ICON_SIZE)
                : null;
        this.inactiveIcon = (icon != null && inactiveColor != null)
                ? icon.get(inactiveColor, UITheme.ICON_SIZE)
                : null;
        applyIcon();
        return this;
    }

    public SidebarButton onClick(Runnable action) {
        super.onClick(action);
        return this;
    }

    public void setActive(boolean isActive) {
        this.active = isActive;
        setBackground(isActive ? UITheme.PRIMARY : UITheme.BG_DARK);
        setForeground(isActive ? UITheme.TEXT_DARK : UITheme.TEXT_LIGHT);
        setFont(isActive ? UITheme.FONT_SUBTITLE : UITheme.FONT_BODY);
        applyIcon();
    }

    private void applyIcon() {
        ImageIcon targetIcon = active ? activeIcon : inactiveIcon;
        if (targetIcon != null) {
            setIcon(targetIcon);
        }
    }
}
