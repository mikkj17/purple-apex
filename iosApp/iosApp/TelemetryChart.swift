import SwiftUI
import Charts
import ComposeApp

class IOSNativeViewFactory : NativeViewFactory {
    static var shared = IOSNativeViewFactory()
    func createChartView(data: [String : [KotlinInt]]) -> UIViewController {
        let view = TelemetryChart()
        
        return UIHostingController(rootView: view)
    }
}

struct TelemetryChart: View {
    var body: some View {
        Chart {
            ForEach(1..<6, id: \.self) { x in
                LineMark(
                    x: .value("X", x),
                    y: .value("Y", x * x)
                )
            }
        }
        .chartYAxis {
            AxisMarks()
        }
        .chartXAxis {
            AxisMarks()
        }
        .frame(height: 300)
    }
}
