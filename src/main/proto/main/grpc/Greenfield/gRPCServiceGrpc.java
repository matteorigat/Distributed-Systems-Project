package Greenfield;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 * <pre>
 * Defining a Service, a Service can have multiple RPC operations
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.25.0)",
    comments = "Source: gRPCService.proto")
public final class gRPCServiceGrpc {

  private gRPCServiceGrpc() {}

  public static final String SERVICE_NAME = "Greenfield.gRPCService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<Greenfield.GRPCService.HelloRequest,
      Greenfield.GRPCService.MechanicResponse> getHelloMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "hello",
      requestType = Greenfield.GRPCService.HelloRequest.class,
      responseType = Greenfield.GRPCService.MechanicResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<Greenfield.GRPCService.HelloRequest,
      Greenfield.GRPCService.MechanicResponse> getHelloMethod() {
    io.grpc.MethodDescriptor<Greenfield.GRPCService.HelloRequest, Greenfield.GRPCService.MechanicResponse> getHelloMethod;
    if ((getHelloMethod = gRPCServiceGrpc.getHelloMethod) == null) {
      synchronized (gRPCServiceGrpc.class) {
        if ((getHelloMethod = gRPCServiceGrpc.getHelloMethod) == null) {
          gRPCServiceGrpc.getHelloMethod = getHelloMethod =
              io.grpc.MethodDescriptor.<Greenfield.GRPCService.HelloRequest, Greenfield.GRPCService.MechanicResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "hello"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  Greenfield.GRPCService.HelloRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  Greenfield.GRPCService.MechanicResponse.getDefaultInstance()))
              .setSchemaDescriptor(new gRPCServiceMethodDescriptorSupplier("hello"))
              .build();
        }
      }
    }
    return getHelloMethod;
  }

  private static volatile io.grpc.MethodDescriptor<Greenfield.GRPCService.MechanicRequest,
      Greenfield.GRPCService.OkResponse> getMechanicMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "mechanic",
      requestType = Greenfield.GRPCService.MechanicRequest.class,
      responseType = Greenfield.GRPCService.OkResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<Greenfield.GRPCService.MechanicRequest,
      Greenfield.GRPCService.OkResponse> getMechanicMethod() {
    io.grpc.MethodDescriptor<Greenfield.GRPCService.MechanicRequest, Greenfield.GRPCService.OkResponse> getMechanicMethod;
    if ((getMechanicMethod = gRPCServiceGrpc.getMechanicMethod) == null) {
      synchronized (gRPCServiceGrpc.class) {
        if ((getMechanicMethod = gRPCServiceGrpc.getMechanicMethod) == null) {
          gRPCServiceGrpc.getMechanicMethod = getMechanicMethod =
              io.grpc.MethodDescriptor.<Greenfield.GRPCService.MechanicRequest, Greenfield.GRPCService.OkResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "mechanic"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  Greenfield.GRPCService.MechanicRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  Greenfield.GRPCService.OkResponse.getDefaultInstance()))
              .setSchemaDescriptor(new gRPCServiceMethodDescriptorSupplier("mechanic"))
              .build();
        }
      }
    }
    return getMechanicMethod;
  }

  private static volatile io.grpc.MethodDescriptor<Greenfield.GRPCService.OkResponse,
      Greenfield.GRPCService.OkResponse> getOkMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ok",
      requestType = Greenfield.GRPCService.OkResponse.class,
      responseType = Greenfield.GRPCService.OkResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<Greenfield.GRPCService.OkResponse,
      Greenfield.GRPCService.OkResponse> getOkMethod() {
    io.grpc.MethodDescriptor<Greenfield.GRPCService.OkResponse, Greenfield.GRPCService.OkResponse> getOkMethod;
    if ((getOkMethod = gRPCServiceGrpc.getOkMethod) == null) {
      synchronized (gRPCServiceGrpc.class) {
        if ((getOkMethod = gRPCServiceGrpc.getOkMethod) == null) {
          gRPCServiceGrpc.getOkMethod = getOkMethod =
              io.grpc.MethodDescriptor.<Greenfield.GRPCService.OkResponse, Greenfield.GRPCService.OkResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ok"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  Greenfield.GRPCService.OkResponse.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  Greenfield.GRPCService.OkResponse.getDefaultInstance()))
              .setSchemaDescriptor(new gRPCServiceMethodDescriptorSupplier("ok"))
              .build();
        }
      }
    }
    return getOkMethod;
  }

  private static volatile io.grpc.MethodDescriptor<Greenfield.GRPCService.QuitRequest,
      Greenfield.GRPCService.QuitRequest> getQuitMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "quit",
      requestType = Greenfield.GRPCService.QuitRequest.class,
      responseType = Greenfield.GRPCService.QuitRequest.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<Greenfield.GRPCService.QuitRequest,
      Greenfield.GRPCService.QuitRequest> getQuitMethod() {
    io.grpc.MethodDescriptor<Greenfield.GRPCService.QuitRequest, Greenfield.GRPCService.QuitRequest> getQuitMethod;
    if ((getQuitMethod = gRPCServiceGrpc.getQuitMethod) == null) {
      synchronized (gRPCServiceGrpc.class) {
        if ((getQuitMethod = gRPCServiceGrpc.getQuitMethod) == null) {
          gRPCServiceGrpc.getQuitMethod = getQuitMethod =
              io.grpc.MethodDescriptor.<Greenfield.GRPCService.QuitRequest, Greenfield.GRPCService.QuitRequest>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "quit"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  Greenfield.GRPCService.QuitRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  Greenfield.GRPCService.QuitRequest.getDefaultInstance()))
              .setSchemaDescriptor(new gRPCServiceMethodDescriptorSupplier("quit"))
              .build();
        }
      }
    }
    return getQuitMethod;
  }

  private static volatile io.grpc.MethodDescriptor<Greenfield.GRPCService.Alive,
      Greenfield.GRPCService.Alive> getAliveMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "alive",
      requestType = Greenfield.GRPCService.Alive.class,
      responseType = Greenfield.GRPCService.Alive.class,
      methodType = io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
  public static io.grpc.MethodDescriptor<Greenfield.GRPCService.Alive,
      Greenfield.GRPCService.Alive> getAliveMethod() {
    io.grpc.MethodDescriptor<Greenfield.GRPCService.Alive, Greenfield.GRPCService.Alive> getAliveMethod;
    if ((getAliveMethod = gRPCServiceGrpc.getAliveMethod) == null) {
      synchronized (gRPCServiceGrpc.class) {
        if ((getAliveMethod = gRPCServiceGrpc.getAliveMethod) == null) {
          gRPCServiceGrpc.getAliveMethod = getAliveMethod =
              io.grpc.MethodDescriptor.<Greenfield.GRPCService.Alive, Greenfield.GRPCService.Alive>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "alive"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  Greenfield.GRPCService.Alive.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  Greenfield.GRPCService.Alive.getDefaultInstance()))
              .setSchemaDescriptor(new gRPCServiceMethodDescriptorSupplier("alive"))
              .build();
        }
      }
    }
    return getAliveMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static gRPCServiceStub newStub(io.grpc.Channel channel) {
    return new gRPCServiceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static gRPCServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new gRPCServiceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static gRPCServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new gRPCServiceFutureStub(channel);
  }

  /**
   * <pre>
   * Defining a Service, a Service can have multiple RPC operations
   * </pre>
   */
  public static abstract class gRPCServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public void hello(Greenfield.GRPCService.HelloRequest request,
        io.grpc.stub.StreamObserver<Greenfield.GRPCService.MechanicResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getHelloMethod(), responseObserver);
    }

    /**
     */
    public void mechanic(Greenfield.GRPCService.MechanicRequest request,
        io.grpc.stub.StreamObserver<Greenfield.GRPCService.OkResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getMechanicMethod(), responseObserver);
    }

    /**
     */
    public void ok(Greenfield.GRPCService.OkResponse request,
        io.grpc.stub.StreamObserver<Greenfield.GRPCService.OkResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getOkMethod(), responseObserver);
    }

    /**
     */
    public void quit(Greenfield.GRPCService.QuitRequest request,
        io.grpc.stub.StreamObserver<Greenfield.GRPCService.QuitRequest> responseObserver) {
      asyncUnimplementedUnaryCall(getQuitMethod(), responseObserver);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<Greenfield.GRPCService.Alive> alive(
        io.grpc.stub.StreamObserver<Greenfield.GRPCService.Alive> responseObserver) {
      return asyncUnimplementedStreamingCall(getAliveMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getHelloMethod(),
            asyncServerStreamingCall(
              new MethodHandlers<
                Greenfield.GRPCService.HelloRequest,
                Greenfield.GRPCService.MechanicResponse>(
                  this, METHODID_HELLO)))
          .addMethod(
            getMechanicMethod(),
            asyncServerStreamingCall(
              new MethodHandlers<
                Greenfield.GRPCService.MechanicRequest,
                Greenfield.GRPCService.OkResponse>(
                  this, METHODID_MECHANIC)))
          .addMethod(
            getOkMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                Greenfield.GRPCService.OkResponse,
                Greenfield.GRPCService.OkResponse>(
                  this, METHODID_OK)))
          .addMethod(
            getQuitMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                Greenfield.GRPCService.QuitRequest,
                Greenfield.GRPCService.QuitRequest>(
                  this, METHODID_QUIT)))
          .addMethod(
            getAliveMethod(),
            asyncBidiStreamingCall(
              new MethodHandlers<
                Greenfield.GRPCService.Alive,
                Greenfield.GRPCService.Alive>(
                  this, METHODID_ALIVE)))
          .build();
    }
  }

  /**
   * <pre>
   * Defining a Service, a Service can have multiple RPC operations
   * </pre>
   */
  public static final class gRPCServiceStub extends io.grpc.stub.AbstractStub<gRPCServiceStub> {
    private gRPCServiceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private gRPCServiceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected gRPCServiceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new gRPCServiceStub(channel, callOptions);
    }

    /**
     */
    public void hello(Greenfield.GRPCService.HelloRequest request,
        io.grpc.stub.StreamObserver<Greenfield.GRPCService.MechanicResponse> responseObserver) {
      asyncServerStreamingCall(
          getChannel().newCall(getHelloMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void mechanic(Greenfield.GRPCService.MechanicRequest request,
        io.grpc.stub.StreamObserver<Greenfield.GRPCService.OkResponse> responseObserver) {
      asyncServerStreamingCall(
          getChannel().newCall(getMechanicMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void ok(Greenfield.GRPCService.OkResponse request,
        io.grpc.stub.StreamObserver<Greenfield.GRPCService.OkResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getOkMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void quit(Greenfield.GRPCService.QuitRequest request,
        io.grpc.stub.StreamObserver<Greenfield.GRPCService.QuitRequest> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getQuitMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<Greenfield.GRPCService.Alive> alive(
        io.grpc.stub.StreamObserver<Greenfield.GRPCService.Alive> responseObserver) {
      return asyncBidiStreamingCall(
          getChannel().newCall(getAliveMethod(), getCallOptions()), responseObserver);
    }
  }

  /**
   * <pre>
   * Defining a Service, a Service can have multiple RPC operations
   * </pre>
   */
  public static final class gRPCServiceBlockingStub extends io.grpc.stub.AbstractStub<gRPCServiceBlockingStub> {
    private gRPCServiceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private gRPCServiceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected gRPCServiceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new gRPCServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public java.util.Iterator<Greenfield.GRPCService.MechanicResponse> hello(
        Greenfield.GRPCService.HelloRequest request) {
      return blockingServerStreamingCall(
          getChannel(), getHelloMethod(), getCallOptions(), request);
    }

    /**
     */
    public java.util.Iterator<Greenfield.GRPCService.OkResponse> mechanic(
        Greenfield.GRPCService.MechanicRequest request) {
      return blockingServerStreamingCall(
          getChannel(), getMechanicMethod(), getCallOptions(), request);
    }

    /**
     */
    public Greenfield.GRPCService.OkResponse ok(Greenfield.GRPCService.OkResponse request) {
      return blockingUnaryCall(
          getChannel(), getOkMethod(), getCallOptions(), request);
    }

    /**
     */
    public Greenfield.GRPCService.QuitRequest quit(Greenfield.GRPCService.QuitRequest request) {
      return blockingUnaryCall(
          getChannel(), getQuitMethod(), getCallOptions(), request);
    }
  }

  /**
   * <pre>
   * Defining a Service, a Service can have multiple RPC operations
   * </pre>
   */
  public static final class gRPCServiceFutureStub extends io.grpc.stub.AbstractStub<gRPCServiceFutureStub> {
    private gRPCServiceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private gRPCServiceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected gRPCServiceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new gRPCServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<Greenfield.GRPCService.OkResponse> ok(
        Greenfield.GRPCService.OkResponse request) {
      return futureUnaryCall(
          getChannel().newCall(getOkMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<Greenfield.GRPCService.QuitRequest> quit(
        Greenfield.GRPCService.QuitRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getQuitMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_HELLO = 0;
  private static final int METHODID_MECHANIC = 1;
  private static final int METHODID_OK = 2;
  private static final int METHODID_QUIT = 3;
  private static final int METHODID_ALIVE = 4;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final gRPCServiceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(gRPCServiceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_HELLO:
          serviceImpl.hello((Greenfield.GRPCService.HelloRequest) request,
              (io.grpc.stub.StreamObserver<Greenfield.GRPCService.MechanicResponse>) responseObserver);
          break;
        case METHODID_MECHANIC:
          serviceImpl.mechanic((Greenfield.GRPCService.MechanicRequest) request,
              (io.grpc.stub.StreamObserver<Greenfield.GRPCService.OkResponse>) responseObserver);
          break;
        case METHODID_OK:
          serviceImpl.ok((Greenfield.GRPCService.OkResponse) request,
              (io.grpc.stub.StreamObserver<Greenfield.GRPCService.OkResponse>) responseObserver);
          break;
        case METHODID_QUIT:
          serviceImpl.quit((Greenfield.GRPCService.QuitRequest) request,
              (io.grpc.stub.StreamObserver<Greenfield.GRPCService.QuitRequest>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_ALIVE:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.alive(
              (io.grpc.stub.StreamObserver<Greenfield.GRPCService.Alive>) responseObserver);
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class gRPCServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    gRPCServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return Greenfield.GRPCService.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("gRPCService");
    }
  }

  private static final class gRPCServiceFileDescriptorSupplier
      extends gRPCServiceBaseDescriptorSupplier {
    gRPCServiceFileDescriptorSupplier() {}
  }

  private static final class gRPCServiceMethodDescriptorSupplier
      extends gRPCServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    gRPCServiceMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (gRPCServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new gRPCServiceFileDescriptorSupplier())
              .addMethod(getHelloMethod())
              .addMethod(getMechanicMethod())
              .addMethod(getOkMethod())
              .addMethod(getQuitMethod())
              .addMethod(getAliveMethod())
              .build();
        }
      }
    }
    return result;
  }
}
