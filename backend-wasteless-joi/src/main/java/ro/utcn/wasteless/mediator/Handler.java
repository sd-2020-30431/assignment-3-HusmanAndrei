package ro.utcn.wasteless.mediator;

public interface Handler<Request, Response> {
    Response handle(Request request);
}
