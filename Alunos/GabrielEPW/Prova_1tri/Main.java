import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

class Client {
    private String id;
    private String name;
    private String email;

    public Client(String name, String email) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}

abstract class Event {
    protected String id;
    protected String name;
    protected LocalDate startDate;
    protected LocalDate endDate;
    protected int maxCapacity;
    protected double dailyTicketPrice;
    protected List<Ticket> ticketsSold;

    public Event(String name, LocalDate startDate, LocalDate endDate, int maxCapacity, double dailyTicketPrice) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.maxCapacity = maxCapacity;
        this.dailyTicketPrice = dailyTicketPrice;
        this.ticketsSold = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public abstract boolean hasVipArea();

    public abstract double getVipTicketPrice();

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public double getDailyTicketPrice() {
        return dailyTicketPrice;
    }

    public List<Ticket> getTicketsSold() {
        return ticketsSold;
    }

    public long getDays() {
        return startDate.datesUntil(endDate.plusDays(1)).count();
    }

    public boolean isDateValid(LocalDate date) {
        return !date.isBefore(startDate) && !date.isAfter(endDate);
    }
}

class Show extends Event {
    private int vipCapacity;

    public Show(String name, LocalDate startDate, LocalDate endDate, int maxCapacity, double dailyTicketPrice) {
        super(name, startDate, endDate, maxCapacity, dailyTicketPrice);
        this.vipCapacity = (int) (maxCapacity * 0.1); // 10% das vagas
    }

    @Override
    public boolean hasVipArea() {
        return true;
    }

    @Override
    public double getVipTicketPrice() {
        return dailyTicketPrice * 1.5; // 50% mais caro
    }

    public int getVipCapacity() {
        return vipCapacity;
    }
}

class Congress extends Event {
    public Congress(String name, LocalDate startDate, LocalDate endDate, int maxCapacity, double dailyTicketPrice) {
        super(name, startDate, endDate, maxCapacity, dailyTicketPrice);
    }

    @Override
    public boolean hasVipArea() {
        return false;
    }

    @Override
    public double getVipTicketPrice() {
        return 0;
    }
}

class Ticket {
    private String id;
    private Client client;
    private Event event;
    private LocalDate date;
    private boolean isVip;
    private boolean used;

    public Ticket(Client client, Event event, LocalDate date, boolean isVip) {
        this.id = UUID.randomUUID().toString();
        this.client = client;
        this.event = event;
        this.date = date;
        this.isVip = isVip;
        this.used = false;
    }

    public String getId() {
        return id;
    }

    public Client getClient() {
        return client;
    }

    public Event getEvent() {
        return event;
    }

    public LocalDate getDate() {
        return date;
    }

    public boolean isVip() {
        return isVip;
    }

    public boolean isUsed() {
        return used;
    }

    public void useTicket() {
        this.used = true;
    }

    public double calculatePrice() {
        return isVip ? event.getVipTicketPrice() : event.getDailyTicketPrice();
    }
}

class EventManagementSystem {
    private List<Client> clients;
    private List<Event> events;
    private Map<String, Ticket> tickets;

    public EventManagementSystem() {
        this.clients = new ArrayList<>();
        this.events = new ArrayList<>();
        this.tickets = new HashMap<>();
    }

    public void registerClient(String name, String email) {
        Client client = new Client(name, email);
        clients.add(client);
        System.out.println("Cliente cadastrado: " + name);
    }

    public void createShow(String name, LocalDate startDate, LocalDate endDate, int maxCapacity, double dailyTicketPrice) {
        Show show = new Show(name, startDate, endDate, maxCapacity, dailyTicketPrice);
        events.add(show);
        System.out.println("Show criado: " + name);
    }

    public void createCongress(String name, LocalDate startDate, LocalDate endDate, int maxCapacity, double dailyTicketPrice) {
        Congress congress = new Congress(name, startDate, endDate, maxCapacity, dailyTicketPrice);
        events.add(congress);
        System.out.println("Congresso criado: " + name);
    }

    public void listEvents() {
        System.out.println("\nLista de Eventos:");
        for (Event event : events) {
            String type = event instanceof Show ? "Show" : "Congresso";
            System.out.printf("%s - %s (%s a %s, Capacidade: %d, Ingresso: R$%.2f)\n",
                    type, event.getName(), event.getStartDate(), event.getEndDate(),
                    event.getMaxCapacity(), event.getDailyTicketPrice());
            if (event.hasVipArea()) {
                System.out.printf("  Área VIP: R$%.2f (Capacidade: %d)\n",
                        event.getVipTicketPrice(), ((Show) event).getVipCapacity());
            }
        }
    }

    public boolean buyTicket(String clientId, String eventId, LocalDate date, boolean isVip) {
        Client client = findClient(clientId);
        Event event = findEvent(eventId);

        if (client == null || event == null) {
            System.out.println("Cliente ou evento não encontrado.");
            return false;
        }

        if (!event.isDateValid(date)) {
            System.out.println("Data inválida para o evento.");
            return false;
        }

        if (!isAvailable(event, date, isVip)) {
            System.out.println("Não há vagas disponíveis.");
            return false;
        }

        if (isVip && !event.hasVipArea()) {
            System.out.println("Este evento não possui área VIP.");
            return false;
        }

        Ticket ticket = new Ticket(client, event, date, isVip);
        event.getTicketsSold().add(ticket);
        tickets.put(ticket.getId(), ticket);
        System.out.printf("Ingresso comprado! Valor: R$%.2f\n", ticket.calculatePrice());
        return true;
    }

    public boolean useTicket(String ticketId) {
        Ticket ticket = tickets.get(ticketId);
        if (ticket == null) {
            System.out.println("Ingresso não encontrado.");
            return false;
        }

        if (ticket.isUsed()) {
            System.out.println("Ingresso já foi utilizado.");
            return false;
        }

        ticket.useTicket();
        System.out.println("Ingresso utilizado com sucesso!");
        return true;
    }

    public double calculateTicketPrice(String eventId, boolean isVip) {
        Event event = findEvent(eventId);
        if (event == null) {
            return 0;
        }
        return isVip && event.hasVipArea() ? event.getVipTicketPrice() : event.getDailyTicketPrice();
    }

    public boolean isAvailable(Event event, LocalDate date, boolean isVip) {
        long ticketsForDate = event.getTicketsSold().stream()
                .filter(t -> t.getDate().equals(date) && t.isVip() == isVip)
                .count();

        if (isVip && event instanceof Show) {
            return ticketsForDate < ((Show) event).getVipCapacity();
        }
        return ticketsForDate < event.getMaxCapacity();
    }

    private Client findClient(String clientId) {
        return clients.stream()
                .filter(c -> c.getId().equals(clientId))
                .findFirst()
                .orElse(null);
    }

    private Event findEvent(String eventId) {
        return events.stream()
                .filter(e -> e.getId().equals(eventId))
                .findFirst()
                .orElse(null);
    }

    // Exemplo de uso
    public static void main(String[] args) {
        EventManagementSystem system = new EventManagementSystem();

        // Cadastro de clientes
        system.registerClient("João Silva", "joao@email.com");
        system.registerClient("Maria Souza", "maria@email.com");
        
        system.createShow("Rock Festival", LocalDate.of(2025, 5, 1),
                LocalDate.of(2025, 5, 3), 1000, 100.0);
        system.createCongress("Tech Conference", LocalDate.of(2025, 6, 1),
                LocalDate.of(2025, 6, 2), 500, 200.0);

        system.listEvents();

    }
}