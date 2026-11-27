# UML Class Diagram — Frontend (`Main` and `UI`)

This document complements [backend-class-diagram.md](backend-class-diagram.md) with the complete Employee Management System (EMS) frontend (`Main` and all `UI.*` packages).

To keep every diagram readable with zero overlapping boxes, zero hidden elements, and no tangled cross-cutting lines, the frontend is organized into five focused views:

1. **Full Frontend Architecture Overview:** All 28 frontend classes (plus inner class `DataTable.Model` and backend services) grouped cleanly by package.
2. **Shell & Pages (`Main`, `UI.Shell`, `UI.Pages`):** Application startup, routing, sidebar navigation, and all four pages in full detail.
3. **Forms & Modals (`UI.Forms`, `FormBuilder`, `Modal`):** Modal dialog framework, abstract form base, and all employee/department forms in full detail.
4. **Reusable Components & Theme (`UI.Components`, `UI.Theme`):**
   - **4A. Interactive Controls & Theme:** Buttons, text/password/select inputs, icons, and theme tokens.
   - **4B. Layout, Table & Display Components:** `LabeledField`, `DataTable` + `DataTable.Model`, `StatCard`, and `Divider`.
5. **Swing Superclass Mapping & Design Notes:** Complete `javax.swing` inheritance table and architectural observations.

---

## 1. Full Frontend Architecture Overview

> High-level package & class overview. Detailed fields, methods, `UITheme` references, and widget-level edges are shown in Sections 2–5 so this overview remains compact and readable.

```mermaid
classDiagram
    direction TB

    namespace Entry {
        class Main
    }

    namespace UI_Shell {
        class MainFrame
        class Sidebar
        class AppContext
        class Route {
            <<enumeration>>
        }
        class AppPage {
            <<abstract>>
        }
    }

    namespace Backend_Boundary {
        class DepartmentService
        class EmployeeService
    }

    namespace UI_Pages {
        class LoginPage
        class HomePage
        class EmployeesPage
        class DepartmentsPage
    }

    namespace UI_Forms {
        class EmployeeForm
        class FullTimeForm
        class PartTimeForm
        class DepartmentForm
    }

    namespace UI_Components {
        class StatCard
        class DataTable~T~
        class DataTableModel~T~ {
            <<inner static>>
        }
        class Modal {
            <<utility>>
        }
        class FormBuilder~T~ {
            <<abstract>>
        }
        class AppButton
        class SidebarButton
        class LabeledField~C~
        class InputField
        class PasswordField
        class SelectField~T~
        class Divider
    }

    namespace UI_Theme {
        class UITheme {
            <<utility>>
        }
        class AppIcon {
            <<enumeration>>
        }
        class IconColor {
            <<enumeration>>
        }
    }

    %% Entry & Shell
    Main ..> MainFrame : launches
    MainFrame *-- Sidebar : hosts
    MainFrame *-- AppContext : creates
    MainFrame *-- AppPage : registers
    MainFrame ..> Route : switches
    AppContext --> DepartmentService : exposes
    AppContext --> EmployeeService : exposes
    AppContext ..> Route : navigates

    %% Pages Hierarchy
    AppPage --> AppContext : uses
    AppPage <|-- LoginPage
    AppPage <|-- HomePage
    AppPage <|-- DepartmentsPage
    AppPage <|-- EmployeesPage

    %% Key Page Collaborators
    HomePage *-- StatCard : KPIs
    DepartmentsPage *-- DataTable~T~ : lists
    EmployeesPage *-- DataTable~T~ : lists
    DepartmentsPage ..> Modal : dialogs
    EmployeesPage ..> Modal : dialogs
    DepartmentsPage ..> DepartmentForm : add & edit
    EmployeesPage ..> EmployeeForm : add & edit

    %% Component & Theme Hierarchy
    DataTable~T~ *-- DataTableModel~T~ : model
    Modal ..> FormBuilder~T~ : submits
    FormBuilder~T~ <|-- EmployeeForm
    FormBuilder~T~ <|-- FullTimeForm
    FormBuilder~T~ <|-- PartTimeForm
    FormBuilder~T~ <|-- DepartmentForm
    EmployeeForm o-- FormBuilder~T~ : type cards

    AppButton <|-- SidebarButton
    AppIcon ..> IconColor : folder
    UITheme ..> AppIcon : palette & size
```

---

## 2. Shell & Pages — Detailed Class Diagram (`Main`, `UI.Shell`, `UI.Pages`)

```mermaid
classDiagram
    direction TB

    namespace Entry {
        class Main {
            +main(String[] args)$ void
        }
    }

    namespace UI_Shell {
        class MainFrame {
            -JPanel root
            -CardLayout contentLayout
            -JPanel contentContainer
            -Sidebar sidebar
            -Map~Route, AppPage~ pages
            +MainFrame()
            -registerPage(Route route, AppPage page) void
            +navigate(Route route) void
        }

        class Sidebar {
            -Map~Route, SidebarButton~ navButtons
            +Sidebar(Consumer~Route~ onNavigate)
            -addTitleLine(String text) void
            -addNavButton(Route route, String label, AppIcon icon, Consumer~Route~ onNavigate) void
            +setActiveRoute(Route activeRoute) void
        }

        class Route {
            <<enumeration>>
            LOGIN
            HOME
            EMPLOYEES
            DEPARTMENTS
        }

        class AppContext {
            -DepartmentService departmentService
            -EmployeeService employeeService
            -Consumer~Route~ navigator
            +AppContext(DepartmentService departmentService, EmployeeService employeeService, Consumer~Route~ navigator)
            +departments() DepartmentService
            +employees() EmployeeService
            +navigate(Route route) void
        }

        class AppPage {
            <<abstract>>
            #AppContext context
            #AppPage(AppContext context)
            #createHeader(String titleText) JPanel
            +onShow() void
        }
    }

    namespace UI_Pages {
        class LoginPage {
            -String ADMIN_HASH$
            +hash(String input)$ String
            +LoginPage(AppContext context)
        }

        class HomePage {
            -StatCard employeesCard
            -StatCard departmentsCard
            -StatCard fullTimeCard
            -StatCard partTimeCard
            -StatCard payrollCard
            +HomePage(AppContext context)
            +onShow() void
            -refresh() void
        }

        class EmployeesPage {
            -String[] TABLE_COLUMNS$
            -DataTable~Employee~ table
            -String currentSearch
            -String currDepartmentName
            ~SelectField~String~ deptsBox
            +EmployeesPage(AppContext context)
            +onShow() void
            -refresh() void
            -handleAdd() void
            -handleEdit() void
            -handleDelete() void
            +refreshTable() void
            -toRow(Employee emp) Object[]
        }

        class DepartmentsPage {
            -String[] TABLE_COLUMNS$
            -DataTable~Department~ table
            -String currentSearch
            +DepartmentsPage(AppContext context)
            +onShow() void
            -handleAdd() void
            -handleEdit() void
            -handleDelete() void
            +refreshTable() void
            -toRow(Department dept) Object[]
        }
    }

    namespace Backend_Services {
        class DepartmentService
        class EmployeeService
    }

    %% Entry & Shell Wiring
    Main ..> MainFrame : launches
    MainFrame "1" *-- "1" Sidebar : hosts
    MainFrame "1" *-- "1" AppContext : creates
    MainFrame "1" *-- "4" AppPage : hosts in CardLayout
    MainFrame ..> Route : switches route
    Sidebar ..> Route : nav buttons
    AppContext ..> Route : navigates
    AppContext "1" --> "1" DepartmentService : exposes
    AppContext "1" --> "1" EmployeeService : exposes

    %% Page Inheritance
    AppPage "1" --> "1" AppContext : reads services & navigates
    AppPage <|-- LoginPage
    AppPage <|-- HomePage
    AppPage <|-- EmployeesPage
    AppPage <|-- DepartmentsPage

    %% Page Service Dependencies
    HomePage ..> DepartmentService : dept count
    HomePage ..> EmployeeService : KPI totals
    EmployeesPage ..> DepartmentService : filter options
    EmployeesPage ..> EmployeeService : CRUD & search
    DepartmentsPage ..> DepartmentService : CRUD & search
    DepartmentsPage ..> EmployeeService : payroll stats
```

---

## 3. Forms & Modals — Detailed Class Diagram (`UI.Forms`, `FormBuilder`, `Modal`)

```mermaid
classDiagram
    direction TB

    namespace Dialog_Framework {
        class Modal {
            <<utility>>
            -Modal()
            +show(Component anchor, String title, String submitLabel, FormBuilder~T~ form, Consumer~T~ onSubmit)$ void
            +show(Component anchor, String title, String submitLabel, AppIcon submitIcon, FormBuilder~T~ form, Consumer~T~ onSubmit)$ void
            +showInfo(Component anchor, String message)$ void
            +showWarning(Component anchor, String title, String message)$ void
            +showError(Component anchor, String title, String message)$ void
            +confirm(Component anchor, String title, String message)$ boolean
            -showMessage(Component anchor, String title, String message, Color titleColor, AppButton okBtn)$ void
            -createMessageLabel(String message)$ JLabel
            -createDialog(Component anchor, String title, Color titleColor, Component bodyContent)$ JDialog
            -showWithFooter(JDialog dialog, AppButton defaultBtn, AppButton... footerButtons)$ void
        }

        class FormBuilder~T~ {
            <<abstract>>
            #FormBuilder()
            +build()* T
        }
    }

    namespace UI_Forms {
        class EmployeeForm {
            -SelectField typeBox
            +EmployeeForm(List typeForms)
            +EmployeeForm(List typeForms, Employee initialEmployee)
            +build() Employee
        }

        class FullTimeForm {
            -SelectField~Department~ deptBox
            -InputField nameField
            -InputField ageField
            -InputField positionField
            -InputField salaryField
            -InputField bonusField
            +FullTimeForm(List~Department~ departments)
            +FullTimeForm(List~Department~ departments, Employee initialEmployee)
            -fillFields(Employee employee) void
            -payRow(String label, InputField field, String unit) LabeledField~?~
            +build() Employee
            +toString() String
        }

        class PartTimeForm {
            -SelectField~Department~ deptBox
            -InputField nameField
            -InputField ageField
            -InputField positionField
            -InputField rateField
            -InputField hoursField
            +PartTimeForm(List~Department~ departments)
            +PartTimeForm(List~Department~ departments, Employee initialEmployee)
            -fillFields(Employee employee) void
            -payRow(String label, InputField field) LabeledField~?~
            -payRow(String label, InputField field, String unit) LabeledField~?~
            +build() Employee
            +toString() String
        }

        class DepartmentForm {
            -InputField nameField
            +DepartmentForm(String initialName)
            +build() String
        }
    }

    namespace Domain_Models {
        class Employee {
            <<abstract>>
        }
        class FullTimeEmployee
        class Department
        class PartTimeEmployee
    }

    namespace Form_Controls {
        class SelectField~T~
        class InputField
        class LabeledField~C~
        class Divider
        class AppButton
    }

    %% Modal & FormBase
    Modal ..> FormBuilder~T~ : embeds & submits
    Modal ..> Divider : dividers
    Modal ..> AppButton : footer buttons

    %% Form Inheritance & Composition
    FormBuilder~T~ <|-- EmployeeForm : T = Employee
    FormBuilder~T~ <|-- FullTimeForm : T = Employee
    FormBuilder~T~ <|-- PartTimeForm : T = Employee
    FormBuilder~T~ <|-- DepartmentForm : T = String
    EmployeeForm "1" o-- "1..*" FormBuilder~T~ : type cards

    %% Model Creation
    EmployeeForm ..> Employee : returns
    FullTimeForm ..> FullTimeEmployee : builds
    FullTimeForm ..> Department : selects
    PartTimeForm ..> PartTimeEmployee : builds
    PartTimeForm ..> Department : selects

    %% Control Composition
    EmployeeForm *-- SelectField~T~ : typeBox
    EmployeeForm ..> LabeledField~C~ : type row
    FullTimeForm *-- SelectField~T~ : deptBox
    FullTimeForm *-- InputField : 5 inputs
    FullTimeForm ..> LabeledField~C~ : rows
    FullTimeForm ..> Divider : divider
    PartTimeForm *-- SelectField~T~ : deptBox
    PartTimeForm *-- InputField : 5 inputs
    PartTimeForm ..> LabeledField~C~ : rows
    PartTimeForm ..> Divider : divider
    DepartmentForm *-- InputField : nameField
    DepartmentForm ..> LabeledField~C~ : name row
```

---

## 4. Reusable Components & Theme — Detailed Class Diagrams (`UI.Components`, `UI.Theme`)

### 4A. Interactive Controls & Theme

```mermaid
classDiagram
    direction TB

    namespace UI_Components_Controls {
        class InputField {
            -Consumer~String~ onChange
            -DocumentListener changeListener
            -String lastValue
            +InputField()
            +InputField(String initialText)
            +InputField(String initialText, int columns)
            +value(String text) InputField
            +columns(int columns) InputField
            +onChange(Consumer~String~ onChange) InputField
            +getValue() String
            +commit() void
        }

        class PasswordField {
            +PasswordField()
            +PasswordField(int columns)
            +columns(int columns) PasswordField
            +clear() PasswordField
        }

        class SelectField~T~ {
            +SelectField()
            +SelectField(List~T~ items)
            +options(List~T~ items) SelectField~T~
            +selected(T item) SelectField~T~
            +onChange(Consumer~T~ onChange) SelectField~T~
            +getSelected() T
        }

        class AppButton {
            -IconColor defaultIconColor
            #AppButton(String text, Color background, Color foreground, IconColor defaultIconColor)
            +primary(String text)$ AppButton
            +secondary(String text)$ AppButton
            +danger(String text)$ AppButton
            +secondaryAlt(String text)$ AppButton
            +icon(AppIcon icon) AppButton
            +icon(AppIcon icon, IconColor color) AppButton
            +alignLeft() AppButton
            +alignCenter() AppButton
            +alignX(float alignment) AppButton
            +onClick(Runnable action) AppButton
        }

        class SidebarButton {
            -ImageIcon activeIcon
            -ImageIcon inactiveIcon
            -boolean active
            +SidebarButton(String text)
            +icon(AppIcon icon) SidebarButton
            +icon(AppIcon icon, IconColor activeColor, IconColor inactiveColor) SidebarButton
            +onClick(Runnable action) SidebarButton
            +setActive(boolean isActive) void
            -applyIcon() void
        }
    }

    namespace UI_Theme {
        class UITheme {
            <<utility>>
            -String FONT_FAMILY$
            +Font FONT_TITLE$
            +Font FONT_DISPLAY$
            +Font FONT_BODY$
            +Font FONT_SUBTITLE$
            +Color BG_DARK$
            +Color BG_BASE$
            +Color BORDER$
            +Color SURFACE_DARK$
            +Color SURFACE_BASE$
            +Color PRIMARY$
            +Color PRIMARY_ALT$
            +Color DANGER$
            +Color DANGER_ALT$
            +Color TEXT_DARK$
            +Color TEXT_LIGHT$
            +int PAGE_PADDING$
            +int ICON_SIZE$
            +int BUTTON_HEIGHT$
            +int FORM_CONTROL_COLUMNS$
            +int FORM_CONTROL_WIDTH$
            +int TABLE_ROW_HEIGHT$
            -UITheme()
        }

        class AppIcon {
            <<enumeration>>
            HOME
            LOGIN
            LOGOUT
            ADD
            CANCEL
            TICK
            EDIT
            EMPLOYEE
            DEPARTMENT
            MONEY
            FULL_TIME
            PART_TIME
            -String file
            -AppIcon(String file)
            +get(IconColor color) ImageIcon
            +get(IconColor color, int size) ImageIcon
        }

        class IconColor {
            <<enumeration>>
            DARK
            LIGHT
            ACCENT
            ACCENT_RED
            DANGER
            -String folder
            -IconColor(String folder)
            +getFolder() String
        }
    }

    %% Inheritance
    AppButton <|-- SidebarButton

    %% Theme Dependencies
    InputField ..> UITheme : theme
    PasswordField ..> UITheme : theme
    SelectField~T~ ..> UITheme : theme
    AppButton ..> UITheme : palette & height
    SidebarButton ..> UITheme : active styling

    %% Icon Dependencies
    AppButton ..> AppIcon : loads icon
    AppButton --> IconColor : default tint
    SidebarButton ..> AppIcon : state icons
    SidebarButton ..> IconColor : state tints
    AppIcon ..> IconColor : folder path
```

### 4B. Layout, Table & Display Components

```mermaid
classDiagram
    direction TB

    namespace Swing_Base {
        class JPanel
        class JScrollPane
        class AbstractTableModel {
            <<abstract>>
        }
    }

    namespace UI_Components_Layout_Data {
        class StatCard {
            -JLabel titleLabel
            -JLabel valueLabel
            +StatCard(String title, String value)
            +StatCard(String title, int value)
            +icon(AppIcon icon) StatCard
            +icon(AppIcon icon, IconColor color) StatCard
            +accentBackground(Color color) StatCard
            +setValue(String value) void
            +setValue(int value) void
        }

        class Divider {
            -Divider()
            +horizontal()$ Divider
            +horizontal(Color color)$ Divider
            +vertical()$ Divider
            +vertical(Color color)$ Divider
        }

        class LabeledField~C~ {
            -JLabel label
            -C field
            -LabeledField(String labelText, C field, boolean vertical)
            +horizontal(String label, C field)$ LabeledField~C~
            +vertical(String label, C field)$ LabeledField~C~
            +getLabel() JLabel
            +getField() C
        }

        class DataTable~T~ {
            -JTable table
            -Model~T~ model
            +DataTable(String[] columns, Function~T, Object[]~ rowMapper)
            +setItems(List~T~ items) void
            +getSelectedItem() T
            +getTable() JTable
        }

        class DataTableModel~T~ {
            <<inner static>>
            -String[] columns
            -Function~T, Object[]~ rowMapper
            -List~T~ items
            -List~Object[]~ rows
            ~Model(String[] columns, Function~T, Object[]~ rowMapper)
            ~setItems(List~T~ newItems) void
            ~getItemAt(int row) T
            +getRowCount() int
            +getColumnCount() int
            +getColumnName(int col) String
            +getValueAt(int row, int col) Object
        }
    }

    namespace UI_Theme {
        class AppIcon {
            <<enumeration>>
        }
        class IconColor {
            <<enumeration>>
        }
        class UITheme {
            <<utility>>
        }
    }

    %% Swing Inheritance
    JPanel <|-- StatCard
    JPanel <|-- Divider
    JPanel <|-- LabeledField~C~
    JScrollPane <|-- DataTable~T~
    AbstractTableModel <|-- DataTableModel~T~

    %% Composition & Theme Dependencies
    StatCard "1" *-- "1" Divider : separator
    DataTable~T~ "1" *-- "1" DataTableModel~T~ : private static Model
    StatCard ..> AppIcon : title icon
    StatCard ..> IconColor : icon tint
    StatCard ..> UITheme : palette & fonts
    Divider ..> UITheme : border color
    LabeledField~C~ ..> UITheme : font & width
    DataTable~T~ ..> UITheme : table theme
```

---

## 5. Swing Superclass Mapping & Frontend Design Notes

### Java Swing Superclass Summary

| Package | Class | Superclass / Kind |
| :--- | :--- | :--- |
| `(default)` | `Main` | Standalone entry point |
| `UI.Shell` | `MainFrame` | `extends JFrame` |
| `UI.Shell` | `Sidebar` | `extends JPanel` |
| `UI.Shell` | `AppPage` | `abstract class extends JPanel` |
| `UI.Shell` | `AppContext` | Standalone context holder |
| `UI.Shell` | `Route` | `enum` |
| `UI.Pages` | `LoginPage`, `HomePage`, `EmployeesPage`, `DepartmentsPage` | `extends AppPage` (`JPanel`) |
| `UI.Forms` | `DepartmentForm`, `EmployeeForm`, `FullTimeForm`, `PartTimeForm` | `extends FormBuilder<T>` (`JPanel`) |
| `UI.Components` | `FormBuilder<T>`, `LabeledField<C extends JComponent>`, `Divider`, `StatCard` | `extends JPanel` |
| `UI.Components` | `AppButton` | `extends JButton` |
| `UI.Components` | `SidebarButton` | `extends AppButton` (`JButton`) |
| `UI.Components` | `InputField` | `extends JTextField` |
| `UI.Components` | `PasswordField` | `extends JPasswordField` |
| `UI.Components` | `SelectField<T>` | `extends JComboBox<T>` |
| `UI.Components` | `DataTable<T>` | `extends JScrollPane` |
| `UI.Components` | `DataTable.Model<T>` | `private static class extends AbstractTableModel` |
| `UI.Components` | `Modal` | `final` utility class (creates `JDialog`) |
| `UI.Theme` | `UITheme` | `final` constants class |
| `UI.Theme` | `AppIcon`, `IconColor` | `enum` |

