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
      Greenfield.GRPCService.HelloResponse> getHelloMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "hello",
      requestType = Greenfield.GRPCService.HelloRequest.class,
      responseType = Greenfield.GRPCService.HelloResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
  public static io.grpc.MethodDescriptor<Greenfield.GRPCService.HelloRequest,
      Greenfield.GRPCService.HelloResponse> getHelloMethod() {
    io.grpc.MethodDescriptor<Greenfield.GRPCService.HelloRequest, Greenfield.GRPCService.HelloResponse> getHelloMethod;
    if ((getHelloMethod = gRPCServiceGrpc.getHelloMethod) == null) {
      synchronized (gRPCServiceGrpc.class) {
        if ((getHelloMethod = gRPCServiceGrpc.getHelloMethod) == null) {
          gRPCServiceGrpc.getHelloMethod = getHelloMethod =
              io.grpc.MethodDescriptor.<Greenfield.GRPCService.HelloRequest, Greenfield.GRPCService.HelloResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "hello"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  Greenfield.GRPCService.HelloRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  Greenfield.GRPCService.HelloResponse.getDefaultInstance()))
              .setSchemaDescriptor(new gRPCServiceMethodDescriptorSupplier("hello"))
              .build();
        }
      }
    }
    return getHelloMethod;
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
        io.grpc.stub.StreamObserver<Greenfield.GRPCService.HelloResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getHelloMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getHelloMethod(),
            asyncServerStreamingCall(
              new MethodHandlers<
                Greenfield.GRPCService.HelloRequest,
                Greenfield.GRPCService.HelloResponse>(
                  this, METHODID_HELLO)))
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
        io.grpc.stub.StreamObserver<Greenfield.GRPCService.HelloResponse> responseObserver) {
      asyncServerStreamingCall(
          getChannel().newCall(getHelloMethod(), getCallOptions()), request, responseObserver);
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
    public java.util.Iterator<Greenfield.GRPCService.HelloResponse> hello(
        Greenfield.GRPCService.HelloRequest request) {
      return blockingServerStreamingCall(
          getChannel(), getHelloMethod(), getCallOptions(), request);
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
  }

  private static final int METHODID_HELLO = 0;

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
              (io.grpc.stub.StreamObserver<Greenfield.GRPCService.HelloResponse>) responseObserver);
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
              .build();
        }
      }
    }
    return result;
  }
}
