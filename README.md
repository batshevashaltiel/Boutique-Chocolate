# 🍫 Cocoa Boutique - Premium Chocolate E-Commerce 
### בוטיק הקקאו - חנות שוקולד פרימיום ועבודת יד

פרויקט גמר דינמי ומלא המדמה חנות בוטיק אינטרנטית לשוקולד בלגי, פטיפורים ומארזים יוקרתיים. 
המערכת כוללת ממשק לקוחות מלא ומערכת ניהול (Admin Dashboard) מתקדמת בזמן אמת.

---

## 📺 לצפייה במערכת בפעולה (Video Demo)


---

## 📸 צילומי מסך מתוך האתר (Screenshots)
<table width="100%">
  <tr>
    <td width="50%"><b>קטלוג המוצרים הראשי</b><br/><img src="https://images.unsplash.com/photo-1548907040-4baa42d10919?w=500" width="100%"/></td>
    <td width="50%"><b>לוח בקרה למנהל (Admin Panel)</b><br/><img src="https://images.unsplash.com/photo-1511381939415-e44015466834?w=500" width="100%"/></td>
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
* **מערכת ניהול מלאי (CRUD):** הוספה, עריכה, ומחיקה של מוצרים.
* **התראות מלאי נמוך:** סינון מיוחד שמציג למנהל מוצרים שעומדים לאזול (או אזלו ל-0) לצורך עדכון מהיר.
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
