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
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.25.0)",
    comments = "Source: gRPCService.proto")
public final class gRPCServiceGrpc {

  private gRPCServiceGrpc() {}

  public static final String SERVICE_NAME = "Greenfield.gRPCService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<Greenfield.GRPCService.Hello,
      Greenfield.GRPCService.Hello> getHelloMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "hello",
      requestType = Greenfield.GRPCService.Hello.class,
      responseType = Greenfield.GRPCService.Hello.class,
      methodType = io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
  public static io.grpc.MethodDescriptor<Greenfield.GRPCService.Hello,
      Greenfield.GRPCService.Hello> getHelloMethod() {
    io.grpc.MethodDescriptor<Greenfield.GRPCService.Hello, Greenfield.GRPCService.Hello> getHelloMethod;
    if ((getHelloMethod = gRPCServiceGrpc.getHelloMethod) == null) {
      synchronized (gRPCServiceGrpc.class) {
        if ((getHelloMethod = gRPCServiceGrpc.getHelloMethod) == null) {
          gRPCServiceGrpc.getHelloMethod = getHelloMethod =
              io.grpc.MethodDescriptor.<Greenfield.GRPCService.Hello, Greenfield.GRPCService.Hello>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "hello"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  Greenfield.GRPCService.Hello.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  Greenfield.GRPCService.Hello.getDefaultInstance()))
              .setSchemaDescriptor(new gRPCServiceMethodDescriptorSupplier("hello"))
              .build();
        }
      }
    }
    return getHelloMethod;
  }

  private static volatile io.grpc.MethodDescriptor<Greenfield.GRPCService.Mechanic,
      Greenfield.GRPCService.Mechanic> getMechanicMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "mechanic",
      requestType = Greenfield.GRPCService.Mechanic.class,
      responseType = Greenfield.GRPCService.Mechanic.class,
      methodType = io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
  public static io.grpc.MethodDescriptor<Greenfield.GRPCService.Mechanic,
      Greenfield.GRPCService.Mechanic> getMechanicMethod() {
    io.grpc.MethodDescriptor<Greenfield.GRPCService.Mechanic, Greenfield.GRPCService.Mechanic> getMechanicMethod;
    if ((getMechanicMethod = gRPCServiceGrpc.getMechanicMethod) == null) {
      synchronized (gRPCServiceGrpc.class) {
        if ((getMechanicMethod = gRPCServiceGrpc.getMechanicMethod) == null) {
          gRPCServiceGrpc.getMechanicMethod = getMechanicMethod =
              io.grpc.MethodDescriptor.<Greenfield.GRPCService.Mechanic, Greenfield.GRPCService.Mechanic>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "mechanic"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  Greenfield.GRPCService.Mechanic.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  Greenfield.GRPCService.Mechanic.getDefaultInstance()))
              .setSchemaDescriptor(new gRPCServiceMethodDescriptorSupplier("mechanic"))
              .build();
        }
      }
    }
    return getMechanicMethod;
  }

  private static volatile io.grpc.MethodDescriptor<Greenfield.GRPCService.Ok,
      Greenfield.GRPCService.Ok> getOkMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ok",
      requestType = Greenfield.GRPCService.Ok.class,
      responseType = Greenfield.GRPCService.Ok.class,
      methodType = io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
  public static io.grpc.MethodDescriptor<Greenfield.GRPCService.Ok,
      Greenfield.GRPCService.Ok> getOkMethod() {
    io.grpc.MethodDescriptor<Greenfield.GRPCService.Ok, Greenfield.GRPCService.Ok> getOkMethod;
    if ((getOkMethod = gRPCServiceGrpc.getOkMethod) == null) {
      synchronized (gRPCServiceGrpc.class) {
        if ((getOkMethod = gRPCServiceGrpc.getOkMethod) == null) {
          gRPCServiceGrpc.getOkMethod = getOkMethod =
              io.grpc.MethodDescriptor.<Greenfield.GRPCService.Ok, Greenfield.GRPCService.Ok>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ok"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  Greenfield.GRPCService.Ok.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  Greenfield.GRPCService.Ok.getDefaultInstance()))
              .setSchemaDescriptor(new gRPCServiceMethodDescriptorSupplier("ok"))
              .build();
        }
      }
    }
    return getOkMethod;
  }

  private static volatile io.grpc.MethodDescriptor<Greenfield.GRPCService.GoodBye,
      Greenfield.GRPCService.GoodBye> getGoodbyeMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "goodbye",
      requestType = Greenfield.GRPCService.GoodBye.class,
      responseType = Greenfield.GRPCService.GoodBye.class,
      methodType = io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
  public static io.grpc.MethodDescriptor<Greenfield.GRPCService.GoodBye,
      Greenfield.GRPCService.GoodBye> getGoodbyeMethod() {
    io.grpc.MethodDescriptor<Greenfield.GRPCService.GoodBye, Greenfield.GRPCService.GoodBye> getGoodbyeMethod;
    if ((getGoodbyeMethod = gRPCServiceGrpc.getGoodbyeMethod) == null) {
      synchronized (gRPCServiceGrpc.class) {
        if ((getGoodbyeMethod = gRPCServiceGrpc.getGoodbyeMethod) == null) {
          gRPCServiceGrpc.getGoodbyeMethod = getGoodbyeMethod =
              io.grpc.MethodDescriptor.<Greenfield.GRPCService.GoodBye, Greenfield.GRPCService.GoodBye>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "goodbye"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  Greenfield.GRPCService.GoodBye.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  Greenfield.GRPCService.GoodBye.getDefaultInstance()))
              .setSchemaDescriptor(new gRPCServiceMethodDescriptorSupplier("goodbye"))
              .build();
        }
      }
    }
    return getGoodbyeMethod;
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
   */
  public static abstract class gRPCServiceImplBase implements io.grpc.BindableService {

    /**
     */
    public io.grpc.stub.StreamObserver<Greenfield.GRPCService.Hello> hello(
        io.grpc.stub.StreamObserver<Greenfield.GRPCService.Hello> responseObserver) {
      return asyncUnimplementedStreamingCall(getHelloMethod(), responseObserver);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<Greenfield.GRPCService.Mechanic> mechanic(
        io.grpc.stub.StreamObserver<Greenfield.GRPCService.Mechanic> responseObserver) {
      return asyncUnimplementedStreamingCall(getMechanicMethod(), responseObserver);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<Greenfield.GRPCService.Ok> ok(
        io.grpc.stub.StreamObserver<Greenfield.GRPCService.Ok> responseObserver) {
      return asyncUnimplementedStreamingCall(getOkMethod(), responseObserver);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<Greenfield.GRPCService.GoodBye> goodbye(
        io.grpc.stub.StreamObserver<Greenfield.GRPCService.GoodBye> responseObserver) {
      return asyncUnimplementedStreamingCall(getGoodbyeMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getHelloMethod(),
            asyncBidiStreamingCall(
              new MethodHandlers<
                Greenfield.GRPCService.Hello,
                Greenfield.GRPCService.Hello>(
                  this, METHODID_HELLO)))
          .addMethod(
            getMechanicMethod(),
            asyncBidiStreamingCall(
              new MethodHandlers<
                Greenfield.GRPCService.Mechanic,
                Greenfield.GRPCService.Mechanic>(
                  this, METHODID_MECHANIC)))
          .addMethod(
            getOkMethod(),
            asyncBidiStreamingCall(
              new MethodHandlers<
                Greenfield.GRPCService.Ok,
                Greenfield.GRPCService.Ok>(
                  this, METHODID_OK)))
          .addMethod(
            getGoodbyeMethod(),
            asyncBidiStreamingCall(
              new MethodHandlers<
                Greenfield.GRPCService.GoodBye,
                Greenfield.GRPCService.GoodBye>(
                  this, METHODID_GOODBYE)))
          .build();
    }
  }

  /**
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
    public io.grpc.stub.StreamObserver<Greenfield.GRPCService.Hello> hello(
        io.grpc.stub.StreamObserver<Greenfield.GRPCService.Hello> responseObserver) {
      return asyncBidiStreamingCall(
          getChannel().newCall(getHelloMethod(), getCallOptions()), responseObserver);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<Greenfield.GRPCService.Mechanic> mechanic(
        io.grpc.stub.StreamObserver<Greenfield.GRPCService.Mechanic> responseObserver) {
      return asyncBidiStreamingCall(
          getChannel().newCall(getMechanicMethod(), getCallOptions()), responseObserver);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<Greenfield.GRPCService.Ok> ok(
        io.grpc.stub.StreamObserver<Greenfield.GRPCService.Ok> responseObserver) {
      return asyncBidiStreamingCall(
          getChannel().newCall(getOkMethod(), getCallOptions()), responseObserver);
    }

    /**
     */
    public io.grpc.stub.StreamObserver<Greenfield.GRPCService.GoodBye> goodbye(
        io.grpc.stub.StreamObserver<Greenfield.GRPCService.GoodBye> responseObserver) {
      return asyncBidiStreamingCall(
          getChannel().newCall(getGoodbyeMethod(), getCallOptions()), responseObserver);
    }
  }

  /**
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
  }

  /**
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
  }

  private static final int METHODID_HELLO = 0;
  private static final int METHODID_MECHANIC = 1;
  private static final int METHODID_OK = 2;
  private static final int METHODID_GOODBYE = 3;

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
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_HELLO:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.hello(
              (io.grpc.stub.StreamObserver<Greenfield.GRPCService.Hello>) responseObserver);
        case METHODID_MECHANIC:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.mechanic(
              (io.grpc.stub.StreamObserver<Greenfield.GRPCService.Mechanic>) responseObserver);
        case METHODID_OK:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.ok(
              (io.grpc.stub.StreamObserver<Greenfield.GRPCService.Ok>) responseObserver);
        case METHODID_GOODBYE:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.goodbye(
              (io.grpc.stub.StreamObserver<Greenfield.GRPCService.GoodBye>) responseObserver);
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
              .addMethod(getGoodbyeMethod())
              .build();
        }
      }
    }
    return result;
  }
}
