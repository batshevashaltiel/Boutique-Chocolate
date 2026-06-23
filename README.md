# Cocoa Boutique - Premium Chocolate E-Commerce 
### בוטיק הקקאו - חנות שוקולד פרימיום 

פרויקט גמר דינמי ומלא המדמה חנות בוטיק אינטרנטית לשוקולד בלגי, פטיפורים ומארזים יוקרתיים. 
המערכת כוללת ממשק לקוחות מלא ומערכת ניהול (Admin Dashboard) מתקדמת בזמן אמת.

---

## 📸 צילומי מסך מתוך האתר (Screenshots)

### 🛒 ממשק הלקוח (Client Side)
<table width="100%">
  <tr>
    <td width="50%"><b>קטלוג לקוח דינמי</b><br/><img src="https://github.com/user-attachments/assets/1b96b64b-90eb-4024-b554-65ead588d42a" width="100%"/></td>
    <td width="50%"><b>עגלת הקניות (סל)</b><br/><img src="https://github.com/user-attachments/assets/52412ee6-8f32-4959-baf3-e6464e523cca" width="100%"/></td>
  </tr>
  <tr>
    <td width="50%" colspan="2" align="center"><b> אישור הזמנה ומספר מעקב  </b><br/><img src="https://github.com/user-attachments/assets/6228fe85-1f64-48b7-ad4f-0a8c4bcf16e8" width="70%"/></td>
  </tr>
</table>

### 🔐 ממשק הניהול (Admin Dashboard)
<table width="100%">
  <tr>
    <td width="50%"><b>עמוד הניהול הראשי והסטטיסטיקות</b><br/><img src="https://github.com/user-attachments/assets/185bc51d-eb4f-411e-be73-7437b35301e3" width="100%"/></td>
    <td width="50%"><b>ניהול הזמנות נכנסות</b><br/><img src="https://github.com/user-attachments/assets/8d6811b8-3eb4-4425-8f9a-50235b2bbd5a" width="100%"/></td>
  </tr>
  <tr>
    <td width="50%"><b>טופס הוספת מוצר חדש</b><br/><img src="https://github.com/user-attachments/assets/ea22ef44-c319-4f8d-9295-43a7c9a664d2" width="100%"/></td>
    <td width="50%"><b>טופס עריכת מוצר קיים</b><br/><img src="https://github.com/user-attachments/assets/57a5a703-eb03-400d-bf60-276f0f0ac19c" width="100%"/></td>
  </tr>
  <tr>
    <td width="50%"><b>קטלוג מנהל </b><br/><img src="https://github.com/user-attachments/assets/529ab42a-0b28-4aab-b684-97692518672f" width="100%"/></td>
    <td width="50%"><b>התראות על מלאי נמוך</b><br/><img src="https://github.com/user-attachments/assets/26e56f06-70ee-4d3e-88cb-01e962d06d72" width="100%"/></td>
  </tr>
</table>

---

## 🚀 תכונות מרכזיות (Key Features)

### ✨ ממשק לקוח (Client Side)
* **קטלוג דינמי:** סינון מוצרים לפי קטגוריות וחיפוש חופשי בזמן אמת (AJAX).
* **עגלת קניות חכמה:** שמירת מוצרים, עדכון כמויות דינמי עם בדיקת מלאי, והתראות מעוצבות (SweetAlert2).
* **מנגנון הגנת רכישה:** הפחתת מלאי אוטומטית מבסיס הנתונים מיד עם ביצוע הזמנה וייצור מספר אישור ייחודי.

### 🔐 ממשק מנהל (Admin Dashboard)
* **סטטיסטיקות בזמן אמת:** הצגת סך הכנסות החנות וכמות ההזמנות הנכנסות.
* **מערכת ניהול מלאי (CRUD):** הוספה, עריכה, ומחיקה של מוצרים בהתאמה אישית.
* **התראות מלאי נמוך:** סינון מיוחד שמציג למנהל מוצרים שעומדים לאזול (או אזלו ל-0) לצורך עדכון מלאי מהיר.
* **ניהול הזמנות:** צפייה בפרטי הזמנות מלאים ושינוי סטטוס משלוח.

---

## 🛠 טכנולוגיות וארכיטקטורה (Tech Stack)
* **Backend:** Java EE, Managed Beans (JSF 2.2).
* **Frontend:** JSF (XHTML), JavaScript (AJAX), CSS3 (Flexbox/Grid).
* **Database:** MySQL (JDBC / DAO Architecture).
* **Server:** Apache Tomcat 9.0.
* **Design Libraries:** SweetAlert2, Google Fonts (Heebo & Playfair Display).

---

## 🗄️ כיצד להריץ את הפרויקט מקומית? (Setup Instructions)
1. יש לייבא את הפרויקט לסביבת **Eclipse (Enterprise Edition)**.
2. יש להריץ את קובץ ה-SQL המצורף במאגר (`setup_db.sql`) על שרת ה-MySQL המקומי שלכם.
3. יש לעדכן את פרטי הגישה לבסיס הנתונים בקובץ `Database.java`.
4. יש להגדיר שרת **Apache Tomcat 9** באקליפס ולהריץ את הפרויקט (`Run on Server`).
