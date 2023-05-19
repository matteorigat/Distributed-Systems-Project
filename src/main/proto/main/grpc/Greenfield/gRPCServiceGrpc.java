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
  private static volatile io.grpc.MethodDescriptor<Greenfield.GRPCService.gRPCMessage,
      Greenfield.GRPCService.gRPCMessage> getGrpcMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "grpc",
      requestType = Greenfield.GRPCService.gRPCMessage.class,
      responseType = Greenfield.GRPCService.gRPCMessage.class,
      methodType = io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
  public static io.grpc.MethodDescriptor<Greenfield.GRPCService.gRPCMessage,
      Greenfield.GRPCService.gRPCMessage> getGrpcMethod() {
    io.grpc.MethodDescriptor<Greenfield.GRPCService.gRPCMessage, Greenfield.GRPCService.gRPCMessage> getGrpcMethod;
    if ((getGrpcMethod = gRPCServiceGrpc.getGrpcMethod) == null) {
      synchronized (gRPCServiceGrpc.class) {
        if ((getGrpcMethod = gRPCServiceGrpc.getGrpcMethod) == null) {
          gRPCServiceGrpc.getGrpcMethod = getGrpcMethod =
              io.grpc.MethodDescriptor.<Greenfield.GRPCService.gRPCMessage, Greenfield.GRPCService.gRPCMessage>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.BIDI_STREAMING)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "grpc"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  Greenfield.GRPCService.gRPCMessage.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  Greenfield.GRPCService.gRPCMessage.getDefaultInstance()))
              .setSchemaDescriptor(new gRPCServiceMethodDescriptorSupplier("grpc"))
              .build();
        }
      }
    }
    return getGrpcMethod;
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
    public io.grpc.stub.StreamObserver<Greenfield.GRPCService.gRPCMessage> grpc(
        io.grpc.stub.StreamObserver<Greenfield.GRPCService.gRPCMessage> responseObserver) {
      return asyncUnimplementedStreamingCall(getGrpcMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getGrpcMethod(),
            asyncBidiStreamingCall(
              new MethodHandlers<
                Greenfield.GRPCService.gRPCMessage,
                Greenfield.GRPCService.gRPCMessage>(
                  this, METHODID_GRPC)))
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
    public io.grpc.stub.StreamObserver<Greenfield.GRPCService.gRPCMessage> grpc(
        io.grpc.stub.StreamObserver<Greenfield.GRPCService.gRPCMessage> responseObserver) {
      return asyncBidiStreamingCall(
          getChannel().newCall(getGrpcMethod(), getCallOptions()), responseObserver);
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

  private static final int METHODID_GRPC = 0;

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
        case METHODID_GRPC:
          return (io.grpc.stub.StreamObserver<Req>) serviceImpl.grpc(
              (io.grpc.stub.StreamObserver<Greenfield.GRPCService.gRPCMessage>) responseObserver);
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
              .addMethod(getGrpcMethod())
              .build();
        }
      }
    }
    return result;
  }
}
