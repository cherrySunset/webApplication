package com.example.webapplication;
@WebServlet("/contacts")
public class ContactsServlet extends HttpServlet {
    private ContactDAO contactDAO;

    public void init() {
        contactDAO = new ContactDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Получение данных от клиента
        String phoneNumber = request.getParameter("phoneNumber");
        String lastName = request.getParameter("lastName");

        // Создание нового контакта
        Contact newContact = new Contact(phoneNumber, lastName);

        // Вставка нового контакта в базу данных
        try {
            contactDAO.insertContact(newContact);
            // Получение списка всех контактов после успешной вставки
            List<Contact> allContacts = contactDAO.getAllContacts();
            // Отправка списка всех контактов в ответ клиенту
            // Это может быть сделано, например, в формате JSON или HTML
            // В данном примере предполагается, что данные будут отправлены в виде HTML
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<html><body>");
            out.println("<h1>All Contacts</h1>");
            for (Contact contact : allContacts) {
                out.println("<p>" + contact.getPhoneNumber() + ": " + contact.getLastName() + "</p>");
            }
            out.println("</body></html>");
        } catch (SQLException e) {
            e.printStackTrace();
            // Обработка ошибки вставки контакта в базу данных
            // Можно отправить соответствующий HTTP-статус или сообщение об ошибке
        }
    }
}
